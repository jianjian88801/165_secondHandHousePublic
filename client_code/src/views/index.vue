<template>
	<div class="home">
		<div class="index_top">
			<div class="index_top_title">二手房信息管理系统</div>
			<div class="index_top_right">
				<template v-if="Token">
					<div v-if="date&&weather.city" class="weather_time_view">
						<div class="weather">
							<div class="city">{{weather.city}}</div>
							<div class="wea">
								{{weather.wea}} | {{weather.tem}}℃
							</div>
						</div>
						<div class="time">
							<div class="date">{{date}}</div>
							<div class="timer">{{timer}}</div>
						</div>
					</div>
				</template>
				<el-button v-if="!Token" type="success" icon="User" circle @click="loginClick" />
				<el-button v-if="Token" type="danger" icon="SwitchButton" circle @click="loginOut" />
			</div>
		</div>
		<el-scrollbar class="contain_view">
			<el-scrollbar wrap-class="scrollbar-wrapper" class="menu_scrollbar">
				<el-menu :default-openeds="[]" :unique-opened="false" :default-active="menuIndex" class="menu_view"
					mode="horizontal" :ellipsis="false" @select="menuChange">
					<el-menu-item class="first_item" index="0" @click="menuHandler('/')">
						<template #title>
							<span>首页</span>
						</template>
					</el-menu-item>
					<template v-for="(menu,index) in menuList" :key="menu.menu">
						<el-sub-menu v-if="menu.child.length>1" :index="index+2+''" class="first_item">
							<template #title>
								<span>{{ menu.name }}</span>
							</template>
							<el-menu-item class="menu_item_view" v-for=" (child,sort) in menu.child" :key="sort"
								:index="(index+2)+'-'+sort" @click="menuHandler(child.url)">{{ child.name }}
							</el-menu-item>
						</el-sub-menu>
						<el-menu-item v-else :index="index+2+''" class="first_item" @click="menuHandler(menu.child[0].url)">
							<template #title>
								<span>{{menu.child[0].name}}</span>
							</template>
						</el-menu-item>
					</template>
					<el-menu-item v-if="Token" class="first_item" @click="menuHandler('center')">
						<template #title>
							<span>个人中心</span>
						</template>
					</el-menu-item>
				</el-menu>
			</el-scrollbar>
			<div class="rotation_view">
				<mySwiper :type="3" :data="rotationList" :autoHeight="false" :autoplay="true"
					:loop="false" :navigation="false" :pagination="false" 
					:paginationType="2" :scrollbar="false" :slidesPerView="1" 
					:spaceBetween="20" :centeredSlides="false"
					:freeMode="false" :effectType="1"
					:direction="horizontal">
					<template #default="scope">
						<img :style='{"width":"100%","height":"440px"}' :src="scope.row.value?$config.url + scope.row.value:''">
					</template>
				</mySwiper>
			</div>
			<router-view />
			<el-backtop :right="100" :bottom="100" />
			<div class="bottom_view">
				<img class="bottom_img" src="http://codegen.caihongy.cn/20201114/7856ba26477849ea828f481fa2773a95.jpg" alt="">
				<div class="bottom_company"></div>
				<div class="bottom_record"></div>
				<div class="bottom_desc"></div>
			</div>
		</el-scrollbar>
	</div>
</template>
<script setup>
	import menu from '@/utils/menu'
	import axios from 'axios'
	import {
		ref,
		onBeforeUnmount,
		getCurrentInstance,
		nextTick,
		computed,
		watch,
	} from 'vue';
	import {
		useRouter
	} from 'vue-router';
	import {
		useStore
	} from 'vuex';
	const router = useRouter()
	const context = getCurrentInstance()?.appContext.config.globalProperties;
	const Token = ref('')
	const weather = ref({})
	const date = ref('')
	const timer = ref('')
	const interval = ref(null)
	const getWeather = () => {
		axios({
			method: 'get',
			url: 'http://v0.yiketianqi.com/free/day?appid=69475998&appsecret=rldbX1Zl&city=北京'
		}).then(res => {
			weather.value = res.data
		})
	}
	watch(() => router.currentRoute.value,() => {
		Token.value = context?.$toolUtil.storageGet('frontToken')
		// 获取默认菜单index
		menuIndex.value = context?.$toolUtil.storageGet('menuIndex')?context?.$toolUtil.storageGet('menuIndex'): '0'
	})
	onBeforeUnmount(() => {
		clearInterval(interval.value)
	})
	// 默认菜单index
	const menuIndex = ref('0')
	const init = () => {
		// 获取默认菜单index
		menuIndex.value = context?.$toolUtil.storageGet('menuIndex')?context?.$toolUtil.storageGet('menuIndex'): '0'
		// 获取菜单
		getMenu()
		// 赋值token
		Token.value = context?.$toolUtil.storageGet('frontToken')
		// 时间
		interval.value = setInterval(() => {
			date.value = context?.$toolUtil.getCurDate()
			timer.value = context?.$toolUtil.getCurDateTime().split(' ')[1]
		}, 1000)
		// 天气
		getWeather()
		// 轮播图
		getRotationList()
		if(Token.value){
			getSession()
		}
	}
	// 切换菜单保存index
	const menuChange = (e)=>{
		context?.$toolUtil.storageSet('menuIndex',e)
	}
	// 轮播图
	const rotationList = ref([])
	const baseUrl = context?.$config.url
	const getRotationList = () => {
		context?.$http({
			url: 'config/list',
			method: 'get',
			params: {
				page: 1,
				limit: 3
			}
		}).then(res => {
			rotationList.value = res.data.data.list
		})
	}
	const menuList = ref([])
	const role = ref('')
	const getMenu = () => {
		let params = {
			page: 1,
			limit: 1,
			sort: 'id',
		}
		context?.$http({
			url: "menu/list",
			method: "get",
			params: params
		}).then(res => {
			context?.$toolUtil.storageSet("menus", res.data.data.list[0].menujson);
		})
		menuList.value = context?.$config.menuList
	}
	const loginClick = () => {
		context?.$toolUtil.storageSet('toPath',window.history.state.current)
		router.push('/login')
	}
	const loginOut = () => {
		context?.$toolUtil.message('退出成功', 'success')
		context?.$toolUtil.storageClear()
		router.replace('/index/home')
		context?.$toolUtil.storageSet('menuIndex','0')
		Token.value = ''
	}
	//菜单跳转
	const menuHandler = (name) => {
		if (name == 'center') {
			name = `${context?.$toolUtil.storageGet('frontSessionTable')}Center`
		}
		router.push(name)
	}
	// 获取用户信息
	const getSession = () =>{
		context?.$http({
			url: `${context?.$toolUtil.storageGet('frontSessionTable')}/session`,
			method:'get'
		}).then(res=>{
			context?.$toolUtil.storageSet('userid',res.data.data.id)
			if(context?.$toolUtil.storageGet('frontSessionTable') == 'yonghu'){
				context?.$toolUtil.storageSet("frontName", res.data.data.yonghuzhanghao)
			}
			if(context?.$toolUtil.storageGet('frontSessionTable') == 'yonghu'){
				context?.$toolUtil.storageSet('headportrait',res.data.data.touxiang)
			}
			if(context?.$toolUtil.storageGet('frontSessionTable') == 'maijia'){
				context?.$toolUtil.storageSet("frontName", res.data.data.maijiazhanghao)
			}
			if(context?.$toolUtil.storageGet('frontSessionTable') == 'maijia'){
				context?.$toolUtil.storageSet('headportrait',res.data.data.touxiang)
			}
		})
	}
	init()
</script>
<style lang="scss" scoped>
	/* 头部 */
	.index_top {
		padding: 12px 12%;
		z-index: 1002;
		color: #022c4e;
		display: block;
		border-color: #d8d8d8;
		box-sizing: border-box;
		background: #fff;
		width: 100%;
		justify-content: space-between;
		border-width: 0 0 1px;
		position: fixed;
		border-style: solid;
		height: 80px;
		.index_top_title {
			padding: 0px;
			color: #ef4238;
			font-weight: 500;
			display: flex;
			font-size: 24px;
			line-height: 26px;
			top: 0px;
			background: none;
			width: 22%;
			align-items: center;
			position: absolute;
			text-align: left;
			height: 80px;
		}
		.index_top_right {
			margin: 0px 0 0;
			display: flex;
			align-items: flex-start;
			float: right;
			// 天气时间盒子
			.weather_time_view {
				margin: 16px 20px 0;
				display: none;
				// 天气城市盒子
				.weather {
					border: 0px solid #fff;
					padding: 0 20px;
					flex-direction: row;
					display: flex;
					font-size: 14px;
					border-width: 0 0px 0 0;
					line-height: 1;
					justify-content: center;
					align-items: center;
					// 城市
					.city {
						padding: 0 0 0px;
					}
					// 天气
					.wea {
					}
				}
				// 时间盒子
				.time {
					padding: 0 20px;
					flex-direction: row;
					display: flex;
					font-size: 14px;
					line-height: 1;
					justify-content: center;
					align-items: center;
					// 日期
					.date {
						padding: 0px;
						margin: 0 10px 0 0;
					}
					// 时间
					.timer {
					}
				}
			}
		}
		// 登录按钮
		:deep(.el-button--success) {
			border: 0px solid #ffffff;
			border-radius: 100%;
			padding: 0px;
			margin: 8px 10px 0 0;
			color: #333;
			background: #e2e2e2;
			width: 40px;
			font-size: 18px;
			height: 40px;
		}
		:deep(.el-button--success:hover) {
			background: #e2e2e2;
		}
		// 登出按钮
		:deep(.el-button--danger) {
			border: 0px solid #999;
			border-radius: 100%;
			padding: 0px;
			margin: 8px 0 0;
			color: #999;
			background: #f9f9f9;
			width: 40px;
			font-size: 18px;
			height: 40px;
		}
		:deep(.el-button--danger:hover) {
			background: #f9f9f9;
		}
	}
	// 轮播盒子
	.rotation_view {
		border: 0px solid #fff;
		border-radius: 0px;
		margin: 80px auto 0;
		background: #fff;
		display: block;
		width: 76%;
		height: 440px;
	}
	// 底部
	.bottom_view {
		padding: 20px 7% 20px;
		margin: 20px 0 0;
		color: #91bfd6;
		display: flex;
		font-size: 14px;
		min-height: 150px;
		line-height: 1.5;
		flex-direction: column;
		background: #262426;
		width: 100%;
		justify-content: center;
		align-items: center;
		text-align: center;
		// 图片
		.bottom_img {
			border-radius: 100%;
			object-fit: cover;
			display: none;
			width: 44px;
			height: 44px;
		}
		// 公司
		.bottom_company {
			margin: 10px 0 0;
			color: inherit;
			font-size: inherit;
			line-height: 1;
		}
		// 备案号
		.bottom_record {
			margin: 10px 0 0;
			color: inherit;
			font-size: inherit;
			line-height: 1;
		}
		// 联系方式
		.bottom_desc {
			margin: 10px 0 0;
			color: inherit;
			font-size: inherit;
			line-height: 1;
		}
	}
	
	
	

	.contain_view {
		margin: 0;
		background: #fff;
		position: inherit;
		height: 100vh;
	}
	.el-aside {
		transition: width 0.15s;
		-webkit-transition: width 0.15s;
		-moz-transition: width 0.15s;
		-webkit-transition: width 0.15s;
		-o-transition: width 0.15s;
	}
	.el-sub-menu__hide-arrow{
		display: block !important;
	}
	.menu_scrollbar {
		padding: 0;
		margin: 0px auto 0;
		z-index: 1002;
		border-color: #efefef;
		border-radius: 0px;
		top: 0px;
		left: 30%;
		background: none;
		width: auto;
		border-width: 0 0 0px 0;
		position: absolute;
		border-style: solid;
		height: auto;
	}
	// 总盒子
	.menu_view {
		border: 0;
		border-radius: 8px;
		background: none;
		display: flex;
		justify-content: space-between;
		flex-wrap: wrap;
		// 一级菜单
		:deep(.first_item) {
			border: 0;
			cursor: pointer;
			padding: 0 10px;
			margin: 0 0px 0 0;
			color: #333;
			white-space: nowrap;
			font-weight: 500;
			font-size: 16px;
			line-height: 80px;
			background: none;
			list-style: none;
			text-align: left;
			height: 80px;
			
			.el-sub-menu__title:hover{
				background: none !important;
			}
			// 图标
		
			// 标题
			.el-sub-menu__title,
			span {
				color: inherit !important;
				vertical-align: middle !important;
				font-size: 16px !important;
			}
		
			//箭头
			.el-sub-menu__icon-arrow {
				margin: -3px 0 0 8px;
				color: inherit;
				vertical-align: middle;
				font-size: 12px;
				position: static;
			}
		}
		// 选中
		:deep(.is-active) {
			color: #fff !important;
			background: #ef4238 !important;
			font-weight: 600 !important;
			border-color: #022c4e !important;
			border-width: 0 0 0px !important;
			border-style: solid !important;
		}
		
		// 悬浮
		:deep(.first_item:hover) {
			color: #fff !important;
			background: #ef4238 !important;
			font-weight: 600 !important;
			border-color: #022c4e !important;
			border-width: 0 0 0px !important;
			border-style: solid !important;
		}
	}
</style>
<style lang="scss">
	// 二级
	.menu_popper {
		border:none !important;
		background: none !important;

		// 二级总盒子
		.el-menu--popup {
			border: 0px solid #eee !important;
			border-radius: 0 !important;
			box-shadow: 0px 0px 0px 0px #999 !important;
			padding: 0 !important;
			margin: 0 !important;
			left: -50px !important;
			background: #ef4238 !important;
			width: auto !important;
			position: absolute !important;
		}

		// 二级菜单
		.menu_item_view {
			border-top: 1px solid #fff !important;
			padding: 0 12px !important;
			color: #fff !important;
			background: #ef4238 !important;
			line-height: 48px !important;
			text-align: center !important;
			height: 48px !important;
		}
		
		// 选中
		.is-active {
			padding: 0 12px !important;
			color: #fff !important;
			background: #ef4238 !important;
			line-height: 48px !important;
			height: 48px !important;
		}
		
		// 悬浮
		.menu_item_view:hover {
			padding: 0 12px !important;
			color: #fff !important;
			background: #ef4238 !important;
			line-height: 48px !important;
			height: 48px !important;
		}
	}
</style>