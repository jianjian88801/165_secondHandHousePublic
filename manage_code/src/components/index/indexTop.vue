<template>
	<div class="top_view">
		<div class="top_left_view">
			<div class="fold_view" @click="toggleClick">
				<el-icon class="icons">
					<Fold v-if="!collapse" />
					<Expand v-else />
				</el-icon>
			</div>
		</div>

		<div class="projectTitle">二手房信息管理系统</div>
		<div class="top_right_view">
			<el-dropdown class="avatar-container right-menu-item" trigger="hover">
				<div class="avatar-wrapper">
					<div class="nickname">欢迎 {{$toolUtil.storageGet('adminName')}}</div>
					<img class="user-avatar" src="@/assets/img/avatar.png">
					<el-icon class="el-icon--right">
						<arrow-down />
					</el-icon>
				</div>
				<template #dropdown>
					<el-dropdown-menu slot="dropdown">
						<el-dropdown-item @click="centerClick" v-if="roleName!='管理员'">
							个人中心
						</el-dropdown-item>
						<el-dropdown-item @click="updatepasswordClick">
							修改密码
						</el-dropdown-item>
						<el-dropdown-item v-if="roleName!='管理员'">
							<span style="display:block;" @click="frontClick">系统前台</span>
						</el-dropdown-item>
						<el-dropdown-item>
							<span style="display:block;" @click="onLogout">退出登录</span>
						</el-dropdown-item>
					</el-dropdown-menu>
				</template>
			</el-dropdown>
		</div>
	</div>
</template>

<script setup>
	import axios from 'axios'
	import {
		ElMessageBox
	} from 'element-plus'
	import {
		toRefs,
		defineEmits,
		getCurrentInstance,
		ref,
		onBeforeUnmount
	} from 'vue';
	import { useStore } from 'vuex'
	const store = useStore()
	import {
		useRouter
	} from 'vue-router';
	const props = defineProps({
		collapse: Boolean
	})
	const {
		collapse,
		
	} = toRefs(props)
	
	const router = useRouter()
	const context = getCurrentInstance()?.appContext.config.globalProperties;
	const emit = defineEmits(['collapseChange'])
	const role = context?.$toolUtil.storageGet('sessionTable')
	const roleName = context?.$toolUtil.storageGet('role')
	const toggleClick = () => {
		emit('collapseChange')
	}
	const getSession = () => {
		context?.$http({
			url: `${context?.$toolUtil.storageGet('sessionTable')}/session`,
			method: 'get'
		}).then(res=>{
			context?.$toolUtil.storageSet('userid',res.data.data.id)
			if(context?.$toolUtil.storageGet('sessionTable') == 'yonghu'){
				context?.$toolUtil.storageSet('adminHeadportrait',res.data.data.touxiang)
			}
			if(context?.$toolUtil.storageGet('sessionTable') == 'maijia'){
				context?.$toolUtil.storageSet('adminHeadportrait',res.data.data.touxiang)
			}
		})
	}
	// 退出登录
	const onLogout = () => {
		let toolUtil = context?.$toolUtil
		store.dispatch('delAllCachedViews')
		store.dispatch('delAllVisitedViews')
		toolUtil.storageClear()
		router.replace({
			name: "login"
		});
	}
	// 跳转前台
	const frontClick = () => {
		window.location.href = `${context?.$config.indexUrl}`
	}
	// 个人中心
	const centerClick = () => {
		router.push(`/${role}Center`)
	}
	// 修改密码
	const updatepasswordClick = () => {
		router.push(`/updatepassword`)
	}
	getSession()
</script>

<style lang="scss" scoped>
	// 总盒子
	.top_view {
		padding: 0 10%;
		z-index: 9;
		display: flex;
		font-size: 18px;
		border-color: #990033;
		clip-path: polygon(1% 0, 100% 0, 99% 100%, 0% 100%);
		top: 0;
		left: 1%;
		background: url(http://codegen.caihongy.cn/20221203/70d69e278b8446b8a4a7161d9dc42210.png) repeat-x left bottom,#7ca065;
		width: 98%;
		justify-content: center;
		border-width: 0 0 0px;
		align-items: center;
		position: fixed;
		border-style: solid;
		height: 64px;
		// 左边盒子
		.top_left_view {
			display: flex;
			width: auto;
			align-items: center;
			height: 100%;
			// 折叠按钮盒子
			.fold_view {
				padding: 0 15px;
				display: none;
				// 图标
				.icons {
				}
			}
		}
		// 标题
		.projectTitle{
			padding: 0 20px;
			margin: 0;
			text-shadow: 1px 2px 4px #000;
			color: #fff;
			font-weight: 500;
			width: 100%;
			font-size: 22px;
			line-height: 1;
			text-align: center;
		}
		// 右部盒子
		.top_right_view{
			top: 5px;
			display: flex;
			width: auto;
			justify-content: flex-end;
			position: absolute;
			right: 10%;
			height: 100%;
			order: 3;
			// 头像盒子
			.avatar-container {
				cursor: pointer;
				margin: 0 30px 0 0;
				color: #fff;
				display: flex;
				align-items: center;
				height: 40px;
				.avatar-wrapper {
					margin: 5px 0 0;
					display: flex;
					position: relative;
					align-items: center;
					// 昵称
					.nickname {
						cursor: pointer;
						margin: 0 10px 0 0;
						color: #fff;
						font-size: 16px;
						line-height: 44px;
						order: 2;
					}
					// 头像
					.user-avatar {
						cursor: pointer;
						border-radius: 100%;
						margin: 0 4px 0 0;
						width: 36px;
						height: 36px;
					}
					// 图标
					.el-icon--right {
						margin: 0 10px 0 0;
						color: #eee;
					}
				}
			}
		}
	}
	// 下拉盒子
	.el-dropdown-menu{
		background: #fff;
		min-width: 150px;
		// 下拉盒子itme
		:deep(.el-dropdown-menu__item){
			color: #555;
			background: none;
		}
		// item悬浮
		:deep(.el-dropdown-menu__item:hover){
			color: #fff;
			background: #7ca065;
		}
	}
	
</style>
