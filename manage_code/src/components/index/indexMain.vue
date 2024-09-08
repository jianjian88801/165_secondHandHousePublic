<template>
	<div style="height: 100%;">
		<index-aside :collapse="collapse" :class="collapse?'index-aside-collapse':'index-aside'"></index-aside>
		<el-main class="main_view index_transition" style="max-width:100%" :class="collapse?'main_view-collapse':''">
			<index-header class="index_header index_transition" :collapse="collapse"
				@collapseChange="collapseChange" :style="{'width':'100%','max-width':'100%'}">
			</index-header>
			<index-tags class="index_tags" :style="{'width':'100%','max-width':'100%'}">
			</index-tags>
			<router-view class="router-view index_transition"
				style="background: transparent;max-width:100%">
			</router-view>
		</el-main>
	</div>
</template>

<script setup>
	import IndexAside from '@/components/index/indexMenu'
	import IndexHeader from '@/components/index/indexTop'
	import IndexTags from '@/components/index/indexTags'
	import menu from "@/utils/menu";
	import router from '../../router'
	import {
		ref,
		getCurrentInstance
	} from 'vue'
	const context = getCurrentInstance()?.appContext.config.globalProperties;
	const collapse = ref( false)
	const collapseChange = () => {
		collapse.value = !collapse.value
	}
	const menuList = ref(null)
	const role = ref('')
	const init = () => {
		const menus = menu.list()
		if (menus) {
			menuList.value = menus
		}
		role.value = context?.$toolUtil.storageGet('role')
		for (let i = 0; i < menuList.value.length; i++) {
			if (menuList.value[i].roleName == role.value) {
				menuList.value = menuList.value[i].backMenu;
				break;
			}
		}
		let arr = makeMenu(menuList.value)

		router.addRoute(arr)
	}
	const makeMenu = (menu) => {
		let brr = {
			path: '/1',
			component: () => import('../../views/index'),
			children: []
		}
		for (let x in menu) {
			for (let i in menu[x].child) {
				brr.children.push({
					path: '/' + menu[x].child[i].tableName,
					name: menu[x].child[i].menu,
					component: () => import(`../../views/${menu[x].child[i].tableName}/list.vue`)
				})
			}
		}
		return brr
	}
	// init()
</script>
<style lang="scss" scoped>
	a:hover {
		background: #00c292;
	}

	.el-main {
		padding: 0;
		margin: 0 0 0 240px;
		overflow: hidden;
		background: #f6f6f6;
	}
	.main_view-collapse {
		padding: 0;
		margin: 0 0 0 64px;
	}
	.main_view {
		position: relative;
		padding:0;
	}

	.index-aside {
		padding: 50px 0 0;
		z-index: 1000;
		overflow: hidden;
		top: 64px;
		left: 0;
		background: url(http://codegen.caihongy.cn/20240416/949c4a27b0d84ab88f99681fc52ae618.png) no-repeat center top,url(http://codegen.caihongy.cn/20221203/1057db36d2aa4bd4b375aa0ccb752a40.png) repeat-x left 20px,linear-gradient(180deg, rgba(124,160,101,1) 0%, rgba(124,160,101,1) 100%),#f6f6f6;
		width: 240px;
		border-color: #d9d9d9;
		border-width: 0 0px 0 0;
		position: fixed;
		border-style: solid;
		height: 100%;
	}
	.index-aside-collapse {
		box-shadow: 1px 0 6px  rgba(0, 0, 0, .3);
		overflow: hidden;
		top: 0;
		left: 0;
		background: #303639;
		width: 64px;
		position: fixed;
		height: 100%;
	}

	.index_header {
		width: 100%;
		z-index: 999;
	}

	.index_tags {
		width: 100%;
		z-index: 999;
	}

	.index_transition{
		transition:all .35s;
	}
</style>
