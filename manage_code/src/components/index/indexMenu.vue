<template>
	<div>
		<el-scrollbar wrap-class="scrollbar-wrapper" class="menu_scrollbar">
			<el-menu :default-openeds="[]" :unique-opened="true" default-active="0" class="menu_view"
				:collapse="collapse">
				<el-sub-menu :index="0" @click="menuHandler('')">
					<template #title>
						<i class="iconfont icon-zhuye2" v-if="collapse?true:true"></i>
						<span>首页</span>
					</template>
				</el-sub-menu>
				<el-sub-menu v-for=" (menu,index) in menuList.backMenu" :key="menu.menu" :index="index+2+''">
					<template #title>
						<i class="iconfont" :class="menu.fontClass" v-if="collapse?true:true"></i>
						<span>{{ menu.menu }}</span>
					</template>
					<el-menu-item class="menu_item_view" v-for=" (child,sort) in menu.child" :key="sort"
						:index="(index+2)+'-'+sort" @click="menuHandler(child.tableName,child.menuJump)">{{ child.menu }}
					</el-menu-item>
				</el-sub-menu>
			</el-menu>
		</el-scrollbar>
	</div>
</template>

<script setup>
	import menu from '@/utils/menu'
	import {
		ref,
		toRefs,
		getCurrentInstance,
		nextTick
	} from 'vue';
	const context = getCurrentInstance()?.appContext.config.globalProperties;
	//props
	const props = defineProps({
		collapse: Boolean
	})
	const {
		collapse
	} = toRefs(props)
	//data
	const menuList = ref([])
	const role = ref('')
	const styleChange = () => {
		nextTick(() => {
			document.querySelectorAll('.el-menu-vertical-demo .el-sub-menu .el-menu').forEach(el => {
				el.removeAttribute('style')
				const icon = {
					"border": "none",
					"padding": "0",
					"margin": "10px auto 0",
					"borderRadius": "0px",
					"background": "none",
					"display": "none",
					"width": "100%"
				}
				Object.keys(icon).forEach((key) => {
					el.style[key] = icon[key]
				})
			})
		})
	}
	//权限验证
	const btnAuth = (e,a)=>{
		return context?.$toolUtil.isAuth(e,a)
	}
	const init = () => {
		const menus = menu.list()
		if (menus) {
			menuList.value = menus
		}
		role.value = context?.$toolUtil.storageGet('role')

		for (let i = 0; i < menuList.value.length; i++) {
			if (menuList.value[i].roleName == role.value) {
				menuList.value = menuList.value[i];
				break;
			}
		}
		// styleChange()
	}
	const menuHandler = (name,menuJump) => {
		if(name == 'center'){
			name = `${role.value}Center`
		}
		if(name == 'storeup'){
			name = `storeup?type=${menuJump}`
		}
		if(name == 'exampaper' && menuJump == '12'){
			name = 'exampaperlist'
		}
		if(name == 'examrecord' && menuJump == '22'){
			name = 'examfailrecord'
		}
		let router = context?.$router
		name = '/' + name
		router.push(name)
	}
	init()
</script>

<style lang="scss" scoped>
	// 总盒子
	:deep(.menu_scrollbar) {

		// 菜单盒子-展开样式
		.menu_view {
			border: 0;
			padding: 0 16px 60px;
			background: none;
			height: 100%;

			// 无二级菜单
			.el-menu-item {
				padding: 0 10px 0 30px;
				box-shadow: 0px 0px 0px #4d6a3b;
				margin: 0 auto 10px;
				color: #666;
				background: url(http://codegen.caihongy.cn/20221203/a14e669e5c084257801eac3aa067d8e0.png) left top no-repeat,url(http://clfile.zggen.cn/20240417/8028bee9d6b54bb0922eb164af4b447e.png) right bottom no-repeat,#f6f6f6;
				font-weight: 500;
				border-color: #327e33 #327e33 #c7c5c5 #efeeee;
				border-width: 0px;
				line-height: 44px;
				border-style: solid;
				height: 44px;
				.iconfont {
					margin: 0 5px 0 0;
					color: inherit;
					width: 20px;
					vertical-align: middle;
					font-size: 20px;
					text-align: center;
				}
			}

			// 无二级悬浮
			.el-menu-item:hover {
				padding: 0 10px 0 30px;
				box-shadow: 0px 0px 0px #4d6a3b;
				margin: 0 auto 10px;
				color: rgba(124,160,101,1);
				background: url(http://codegen.caihongy.cn/20221203/a14e669e5c084257801eac3aa067d8e0.png) left top no-repeat,url(http://clfile.zggen.cn/20240417/8028bee9d6b54bb0922eb164af4b447e.png) right bottom no-repeat,#f6f6f6;
				font-weight: 600;
				border-color: #327e33 #327e33 #c7c5c5;
				border-width: 0px;
				line-height: 44px;
				border-style: solid;
				height: 44px;
			}

			// 无二级选中
			.el-menu-item.is-active {
				padding: 0 10px 0 30px;
				box-shadow: 0px 0px 0px #4d6a3b;
				margin: 0 auto 10px;
				color: rgba(124,160,101,1);
				background: url(http://codegen.caihongy.cn/20221203/a14e669e5c084257801eac3aa067d8e0.png) left top no-repeat,url(http://clfile.zggen.cn/20240417/8028bee9d6b54bb0922eb164af4b447e.png) right bottom no-repeat,#f6f6f6;
				font-weight: 600;
				border-color: #327e33 #327e33 #c7c5c5;
				border-width: 0px;
				line-height: 44px;
				border-style: solid;
				height: 44px;
			}

			// 有二级盒子
			.el-sub-menu {
				cursor: pointer;
				padding: 0 0;
				color: #333;
				white-space: nowrap;
				background: none;
				position: relative;

				// 有二级item
				.el-sub-menu__title {
					padding: 0 10px 0 30px;
					box-shadow: 0px 0px 0px #4d6a3b;
					margin: 0 auto 10px;
					color: #666;
					background: url(http://codegen.caihongy.cn/20221203/a14e669e5c084257801eac3aa067d8e0.png) left top no-repeat,url(http://clfile.zggen.cn/20240417/8028bee9d6b54bb0922eb164af4b447e.png) right bottom no-repeat,#f6f6f6;
					font-weight: 500;
					border-color: #327e33 #327e33 #c7c5c5 #efeeee;
					border-width: 0px;
					line-height: 44px;
					border-style: solid;
					height: 44px;
					.iconfont {
						margin: 0 5px 0 0;
						color: inherit;
						width: 20px;
						vertical-align: middle;
						font-size: 20px;
						text-align: center;
					}
					.el-sub-menu__icon-arrow{
						margin: -6px 0 0 8px;
						color: inherit;
						vertical-align: middle;
						font-size: 12px;
						position: absolute;
						right: 10px;
					}
				}

				// 有二级item悬浮
				.el-sub-menu__title:hover {
					padding: 0 10px 0 30px;
					box-shadow: 0px 0px 0px #4d6a3b;
					margin: 0 auto 10px;
					color: rgba(124,160,101,1);
					background: url(http://codegen.caihongy.cn/20221203/a14e669e5c084257801eac3aa067d8e0.png) left top no-repeat,url(http://clfile.zggen.cn/20240417/8028bee9d6b54bb0922eb164af4b447e.png) right bottom no-repeat,#f6f6f6;
					font-weight: 600;
					border-color: #327e33 #327e33 #c7c5c5;
					border-width: 0px;
					line-height: 44px;
					border-style: solid;
					height: 44px;
				}
			}
			//二级选中
			.is-active {
				.el-sub-menu__title {
					padding: 0 10px 0 30px;
					box-shadow: 0px 0px 0px #4d6a3b;
					margin: 0 auto 10px;
					color: rgba(124,160,101,1);
					background: url(http://codegen.caihongy.cn/20221203/a14e669e5c084257801eac3aa067d8e0.png) left top no-repeat,url(http://clfile.zggen.cn/20240417/8028bee9d6b54bb0922eb164af4b447e.png) right bottom no-repeat,#f6f6f6;
					font-weight: 600;
					border-color: #327e33 #327e33 #c7c5c5;
					border-width: 0px;
					line-height: 44px;
					border-style: solid;
					height: 44px;
				}
			}
			// 二级盒子
			.el-menu--inline {
				border: none;
				padding: 0;
				margin: 12px auto 10px;
				background: none;
				width: auto;
				box-sizing: border-box;
				// 二级菜单
				.menu_item_view {
					padding: 0 10px 0 60px;
					box-shadow: none;
					margin: 0 auto 8px;
					color: #666;
					background: url(http://clfile.zggen.cn/20240417/520e48141a194242a6178062e563e0cd.png) left bottom no-repeat,url(http://codegen.caihongy.cn/20221203/8e49820ace4d4c1392bfbbe018036956.png) right top no-repeat,#eee;
					display: block;
					border-color: #fbbe62;
					border-width: 0px;
					line-height: 38px;
					border-style: solid;
					height: 38px;
				}
				// 二级悬浮
				.menu_item_view:hover {
					padding: 0 10px 0 60px;
					margin: 0 auto 8px;
					color: rgba(124,160,101,1);
					background: url(http://clfile.zggen.cn/20240417/520e48141a194242a6178062e563e0cd.png) left bottom no-repeat,url(http://codegen.caihongy.cn/20221203/8e49820ace4d4c1392bfbbe018036956.png) right top no-repeat,#eee;
					font-weight: 600;
					border-color: #fbbe62;
					border-width: 0px;
					line-height: 38px;
					border-style: solid;
					height: 38px;
				}
				// 二级选中
				.is-active.menu_item_view {
					padding: 0 10px 0 60px;
					box-shadow: none;
					margin: 0 auto 8px;
					color: rgba(124,160,101,1);
					background: url(http://clfile.zggen.cn/20240417/520e48141a194242a6178062e563e0cd.png) left bottom no-repeat,url(http://codegen.caihongy.cn/20221203/8e49820ace4d4c1392bfbbe018036956.png) right top no-repeat,#eee;
					font-weight: 600;
					border-color: #fbbe62;
					border-width: 0px;
					line-height: 38px;
					border-style: #fbbe62;
					height: 38px;
				}
			}
		}
		// 菜单盒子-关闭样式
		.el-menu--collapse {
			padding: 0 10px;
			color: #fff;
			background: red;
			height: 100%;

			// 无二级菜单
			.el-menu-item {
				padding: 0 10px;
				color: #666;
				background: #fff;
				line-height: 50px;
				height: 50px;
				.iconfont {
					margin: 0 5px 0 0;
					color: inherit;
					width: 34px;
					vertical-align: middle;
					font-size: 20px;
					text-align: center;
				}
			}

			// 无二级悬浮
			.el-menu-item:hover {
				padding: 0 10px;
				color: #ccc;
				background: gray;
				line-height: 50px;
				height: 50px;
			}

			// 无二级选中
			.el-menu-item.is-active {
				padding: 0 10px;
				color: #ccc;
				background: #123;
				line-height: 50px;
				height: 50px;
			}

			// 有二级盒子
			.el-sub-menu {
				cursor: pointer;
				padding: 0 0;
				color: #333;
				white-space: nowrap;
				background: #fff;
				position: relative;

				// 有二级item
				.el-sub-menu__title {
					padding: 0 10px;
					color: #666;
					background: #fff;
					line-height: 50px;
					height: 50px;
					.iconfont {
						margin: 0 5px 0 0;
						color: inherit;
						width: 34px;
						vertical-align: middle;
						font-size: 20px;
						text-align: center;
					}
					.el-sub-menu__icon-arrow{
						margin: -3px 0 0 8px;
						color: inherit;
						vertical-align: middle;
						font-size: 12px;
						position: static;
					}
				}

				// 有二级item悬浮
				.el-sub-menu__title:hover {
					padding: 0 10px;
					color: #ccc;
					background: gray;
					line-height: 50px;
					height: 50px;
				}
			}
			//二级选中
			.is-active {
				.el-sub-menu__title {
					padding: 0 10px;
					color: #ccc;
					background: #123;
					line-height: 50px;
					height: 50px;
				}
			}
			// 二级盒子
			.el-menu--inline {
				border: none;
				padding: 4px;
				background: #123;
				// 二级菜单
				.menu_item_view {
					padding: 0 40px;
					color: #666;
					background: goldenrod;
					line-height: 50px;
					height: 50px;
				}
				// 二级悬浮
				.menu_item_view:hover {
					padding: 0 40px;
					color: #f00;
					background: greenyellow;
					line-height: 50px;
					height: 50px;
				}
				// 二级选中
				.is-active.menu_item_view {
					padding: 0 40px;
					color: #000;
					background: burlywood;
					line-height: 50px;
					height: 50px;
				}
			}
		}
	}
</style>
<style lang="scss">
	.el-popper{
		.el-menu--popup-container {
			.el-menu--popup{
				border: none;
				padding: 4px;
				background: #123;
				// 二级菜单
				.menu_item_view {
					padding: 0 40px;
					color: #666;
					background: goldenrod;
					line-height: 50px;
					height: 50px;
				}
				// 二级悬浮
				.menu_item_view:hover {
					padding: 0 40px;
					color: #f00;
					background: greenyellow;
					line-height: 50px;
					height: 50px;
				}
				// 二级选中
				.is-active.menu_item_view {
					padding: 0 40px;
					color: #000;
					background: burlywood;
					line-height: 50px;
					height: 50px;
				}
			}
		}
	}
</style>
