<template>
	<div class="home_view">
		<div class="projectTitle">欢迎使用 {{projectName}}</div>
	</div>
</template>

<script setup>
	import {
		inject,
		nextTick,
		ref,
		getCurrentInstance
	} from 'vue';
	const context = getCurrentInstance()?.appContext.config.globalProperties;
	const projectName = context.$project.projectName
	const init=()=>{
	}
	//权限验证
	const btnAuth = (e,a)=>{
		return context?.$toolUtil.isAuth(e,a)
	}
	init()
</script>
<style lang="scss">
	.projectTitle{
		padding: 30px 0 20px;
		margin: 20px 0 20px;
		color: #333;
		font-weight: bold;
		display: flex;
		width: 100%;
		font-size: 30px;
		justify-content: center;
		align-items: center;
		height: auto;
	}

	.showIcons {
		transition: transform 0.3s;
		margin-right: 10px;
	}

	.showIcons1 {
		transform: rotate(-180deg);
	}
	
	// 总数盒子
	.count_list{
		padding: 0 0 20px;
		display: flex;
		width: 100%;
		justify-content: center;
		align-items: flex-start;
		flex-wrap: wrap;
		// 总数card
		.card_view {
			border: 0px solid #fccb8250;
			border-radius: 0;
			box-shadow: 0px 0px 0px rgba(0,0,0,.12);
			margin: 0 1% 20px;
			background: none;
			width: 23%;
			box-sizing: border-box;
			height: auto;
			// card头部
			.el-card__header {
				border: 0px solid #7da066;
				background: #7da06620;
				width: 100%;
				border-width: 0 0 0px;
				// 头部盒子
				.index_card_head {
					display: flex;
					width: 100%;
					justify-content: space-between;
					align-items: center;
					height: 10px;
					// 标题
					.card_head_title {
						color: #333;
						font-size: 14px;
					}
					// 按钮盒子
					.card_head_right {
						display: flex;
						align-items: center;
						// 按钮
						.el-icon {
							cursor: pointer;
							color: #333;
							font-size: 20px;
						}
					}
				}
			}
			// body
			.el-card__body {
				border-radius: 0 0 20px 20px;
				padding: 0;
				background: #fff;
				border-color: #7da06630;
				border-width: 0 1px 1px;
				border-style: solid;
				// body盒子
				.count_item{
					padding: 30px;
					flex-direction: column;
					display: flex;
					justify-content: center;
					align-items: center;
					text-align: center;
					// 总数标题
					.count_title{
						margin: 0 0px 0 0;
						color: #666;
						font-weight: 500;
						display: inline-block;
						font-size: 16px;
						line-height: 1.5;
					}
					// 总数数字
					.count_num{
						color: #c00;
						font-weight: 600;
						display: inline-block;
						font-size: 26px;
						line-height: 1.5;
					}
				}
			}
		}
	}
	// 首页盒子
	.home_view {
		padding: 0px 20px;
		margin: 0;
		background: url(http://clfile.zggen.cn/20240417/41207a459fdc41d182fa01f86aa9056c.png) no-repeat center top / 100% 100% !important;
		width: 100%;
		min-height: 100vh;
		height: auto;
	}
	// 统计图盒子
	.card_list {
		padding: 0 0 20px;
		display: flex;
		width: 100%;
		justify-content: space-between;
		align-items: flex-start;
		flex-wrap: wrap;
		// 统计图card
		.card_view {
			border: 0px solid #fccb8250;
			border-radius: 0;
			box-shadow: 0px 0px 0px rgba(0,0,0,.12);
			margin: 10px;
			background: none;
			width: calc(50% - 20px);
			box-sizing: border-box;
			height: auto;
			// 头部
			.el-card__header {
				border: 0px solid #eee;
				background: #7da06620;
				width: 100%;
				border-width: 0 0 0px;
				// 头部盒子
				.index_card_head {
					display: flex;
					width: 100%;
					justify-content: space-between;
					align-items: center;
					height: 10px;
					// 标题
					.card_head_title {
						color: #333;
						font-size: 14px;
					}
					// 按钮盒子
					.card_head_right {
						display: flex;
						align-items: center;
						// 按钮
						.el-icon{
							cursor: pointer;
							color: #333;
							font-size: 20px;
						}
					}
				}
			}
			// body
			.el-card__body {
				border-radius: 0 0 20px 20px;
				padding: 0;
				background: #fff;
				border-color: #7da06630;
				border-width: 0 1px 1px;
				border-style: solid;
				// body盒子
				.card_item{
					padding: 10px;
					text-align: center;
				}
			}
		}
	}
</style>
