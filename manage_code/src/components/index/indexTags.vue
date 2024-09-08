<template>
	<div id="tags-view-container" class="tags-view-container">
		<scroll-pane ref="scrollPane" class="tags-view-wrapper">
			<router-link v-for="tag in visitedViews" ref="tag" :key="tag.path" :class="isActive(tag)?'active':''"
				:to="{ path: tag.path, query: tag.query, fullPath: tag.fullPath }" tag="span" class="tags-view-item"
				@click.middle.native="closeSelectedTag(tag)" @contextmenu.prevent.native="openMenu(tag,$event)">
				{{ tag.name }}
				<el-icon class="el-icon-close" v-if="!tag.meta.affix" @click.prevent.stop="closeSelectedTag(tag)">
					<Close />
				</el-icon>
			</router-link>
		</scroll-pane>
		<ul v-show="visible" :style="{left:left+'px',top:top+'px'}" class="contextmenu">
			<li v-if="!(selectedTag.meta&&selectedTag.meta.affix)" @click="closeSelectedTag(selectedTag)">Close</li>
			<li @click="closeAllTags(selectedTag)">Close All</li>
		</ul>
	</div>
</template>

<script>
	import ScrollPane from './indexScrollPane'
	import {
		useStore
	} from 'vuex'

	export default {
		components: {
			ScrollPane
		},
		data() {
			return {
				visible: false,
				top: 0,
				left: 0,
				selectedTag: {},
				affixTags: []
			}
		},
		computed: {
			visitedViews() {
				return this.$store.state.visitedViews
			},
			routes() {
				return this.$store.state.routes
			}
		},
		watch: {
			$route() {
				this.addTags()
				this.moveToCurrentTag()
			},
			visible(value) {
				if (value) {
					document.body.addEventListener('click', this.closeMenu)
				} else {
					document.body.removeEventListener('click', this.closeMenu)
				}
			}
		},
		mounted() {
			this.$store.dispatch('updateSideMenus')
			this.initTags()
			this.addTags()
		},
		methods: {
			isActive(route) {
				return route.path === this.$route.path
			},
			filterAffixTags(routes, basePath = '/') {
				let tags = []
				routes.forEach(route => {
					if (route.meta && route.meta.affix) {
						tags.push({
							fullPath: '/',
							path: '/',
							name: route.name,
							meta: {
								...route.meta
							}
						})
					}
					if (route.children) {
						const tempTags = this.filterAffixTags(route.children, route.path)
						if (tempTags.length >= 1) {
							tags = [...tags, ...tempTags]
						}
					}
				})
				return tags
			},
			initTags() {
				const affixTags = this.affixTags = this.filterAffixTags(this.routes)
				for (const tag of affixTags) {
					// Must have tag name
					if (tag.name) {
						this.$store.dispatch('addVisitedView', tag)
					}
				}
			},
			addTags() {
				const {
					name
				} = this.$route
				if (name) {

					this.$store.dispatch('addView', this.$route)
				}
				return false
			},
			moveToCurrentTag() {
				const tags = this.$refs.tag
				this.$nextTick(() => {
					for (const tag of tags) {
						if (tag.to.path === this.$route.path) {
							this.$refs.scrollPane.moveToTarget(tag)
							// when query is different then update
							if (tag.to.fullPath !== this.$route.fullPath) {
								this.$store.dispatch('updateVisitedView', this.$route)
							}
							break
						}
					}
				})
			},
			closeSelectedTag(view) {
				this.$store.dispatch('delView', view).then(({
					visitedViews
				}) => {
					if (this.isActive(view)) {
						this.toLastView(visitedViews, view)
					}
				})
			},
			closeAllTags(view) {

				this.$store.dispatch('delAllViews').then(({
					visitedViews
				}) => {
					if (this.affixTags.some(tag => tag.path === view.path)) {
						return
					}
					this.toLastView(visitedViews, view)
				})
			},
			toLastView(visitedViews, view) {
				const latestView = visitedViews.slice(-1)[0]
				if(latestView.name=='首页'){
					this.$router.push('/')
					return false
				}
				if (latestView) {
					this.$router.push(latestView)
				} else {
					// now the default is to redirect to the home page if there is no tags-view,
					// you can adjust it according to your needs.
					if (view.name === '首页') {
						// to reload home page
						this.$router.replace({
							path: '/redirect' + view.fullPath
						})
					} else {
						this.$router.push('/')
					}
				}
			},
			openMenu(tag, e) {
				const menuMinWidth = 105
				const offsetLeft = this.$el.getBoundingClientRect().left // container margin left
				const offsetWidth = this.$el.offsetWidth // container width
				const maxLeft = offsetWidth - menuMinWidth // left boundary
				const left = e.clientX - offsetLeft + 15 // 15: margin right

				if (left > maxLeft) {
					this.left = maxLeft
				} else {
					this.left = left
				}

				this.top = 36
				this.visible = true
				this.selectedTag = tag
			},
			closeMenu() {
				this.visible = false
			}
		}
	}
</script>

<style lang="scss" scoped>
	.tags-view-container {
		border: 1px solid #eee;
		padding: 0 20px;
		margin: 85px 40px 0 20px;
		clip-path: polygon(1% 0, 100% 0, 99% 100%, 0% 100%);
		background: url(http://codegen.caihongy.cn/20221203/70d69e278b8446b8a4a7161d9dc42210.png) repeat-x left bottom,#7ca065;
		width: calc(100% - 40px)!important;
		border-width: 0 0 2px;
		height: 44px;

		.tags-view-wrapper {
			.tags-view-item {
				cursor: pointer;
				border: 0px solid #990033;
				padding: 0 8px;
				margin: 5px 5px 0 0px;
				color: #eee;
				background: none;
				display: inline-block;
				font-size: 14px;
				line-height: 30px;
				position: relative;
				height: 30px;

				&:first-of-type {
					margin-left: 15px;
				}

				&:last-of-type {
					margin-right: 15px;
				}

				&.active {
					background-color: none;
					padding: 0 8px;
					margin: 0px 5px 0 0px;
					color: #a7e5c4;
					border-color: #eee;

					&::before {
						content: '';
						background: #fff;
						display: inline-block;
						width: 8px;
						height: 8px;
						border-radius: 50%;
						position: relative;
						margin-right: 2px;
					}
				}
			}
		}

		.contextmenu {
			border-radius: 4px;
			padding: 5px 0;
			box-shadow: 2px 2px 3px 0 rgba(0, 0, 0, .3);
			margin: 0;
			z-index: 3000;
			color: #333;
			list-style-type: none;
			background: #fff;
			font-weight: 400;
			font-size: 12px;
			position: absolute;

			li {
				cursor: pointer;
				padding: 7px 16px;
				margin: 0;

				&:hover {
					color: #fff;
					background: #19a97b;
				}
			}
		}
	}
</style>

<style lang="scss">
	a {
		text-decoration-line: none;
	}
	//reset element css of el-icon-close
	.tags-view-wrapper {
		display: flex;
		justify-content: flex-start;

		.tags-view-item {
			.el-icon-close {
				width: 16px;
				height: 16px;
				vertical-align: 2px;
				border-radius: 50%;
				text-align: center;
				transition: all .3s cubic-bezier(.645, .045, .355, 1);
				transform-origin: 100% 50%;
				margin-right: -4px;
				margin-left: 4px;

				&:before {
					transform: scale(.6);
					display: inline-block;
					vertical-align: -3px;
				}

				&:hover {
					background-color: #b4bccc;
					color: #fff;
				}
			}
		}
	}
</style>