
<template>
	<div>
		<div class="app-contain">
			<div class="list_search_view">
				<el-form :model="searchQuery" class="search_form" >
					<div class="search_view">
						<div class="search_label">
							名称：
						</div>
						<div class="search_box">
							<el-input class="search_inp" v-model="searchQuery.name" placeholder="名称"
								clearable>
							</el-input>
						</div>
					</div>
					<div class="search_btn_view">
						<el-button class="search_btn" type="primary" @click="searchClick()" size="small">搜索</el-button>
					</div>
				</el-form>
				<br>
				<div class="btn_view">
					<el-button type="success" @click="addClick" v-if="btnAuth('storeup','新增')">新增</el-button>
					<el-button type="primary" :disabled="selRows.length==1?false:true" @click="editClick" v-if=" btnAuth('storeup','修改')">修改</el-button>
					<el-button type="danger" :disabled="selRows.length?false:true" @click="delClick(null)"  v-if="btnAuth('storeup','删除')">删除</el-button>
				</div>
			</div>
			<br>
			<el-table
				v-loading="listLoading"
				border 
				:stripe='true'
				@selection-change="handleSelectionChange" 
				ref="table"
				v-if="btnAuth('storeup','查看')"
				:data="list"
				@row-click="listChange">
				<el-table-column :resizable='true' align="left" header-align="left" type="selection" width="55" />
				<el-table-column label="序号" width="70" :resizable='true' :sortable='true' align="left" header-align="left">
					<template #default="scope">{{ scope.$index + 1}}</template>
				</el-table-column>
				<el-table-column
					 :resizable='true' 
					 :sortable='true' 
					 align="left" 
					 header-align="left"
					 prop="name"
					label="名称">
					<template #default="scope">
						{{scope.row.name}}
					</template>
				</el-table-column>
				<el-table-column label="图片" width="120" :resizable='true' :sortable='true' align="left" header-align="left">
					<template #default="scope">
						<div v-if="scope.row.picture">
							<el-image v-if="scope.row.picture.substring(0,4)=='http'" preview-teleported
								:preview-src-list="[scope.row.picture.split(',')[0]]"
								:src="scope.row.picture.split(',')[0]" style="width:100px;height:100px"></el-image>
							<el-image v-else preview-teleported
								:preview-src-list="[$config.url+scope.row.picture.split(',')[0]]"
								:src="$config.url+scope.row.picture.split(',')[0]" style="width:100px;height:100px">
							</el-image>
						</div>
						<div v-else>无图片</div>
					</template>
				</el-table-column>
				<el-table-column
					 :resizable='true' 
					 :sortable='true' 
					 align="left" 
					 header-align="left"
					 prop="inteltype"
					label="推荐类型">
					<template #default="scope">
						{{scope.row.inteltype}}
					</template>
				</el-table-column>
				<el-table-column
					 :resizable='true' 
					 :sortable='true' 
					 align="left" 
					 header-align="left"
					 prop="remark"
					label="备注">
					<template #default="scope">
						{{scope.row.remark}}
					</template>
				</el-table-column>
				<el-table-column label="操作" width="300" :resizable='true' :sortable='true' align="left" header-align="left">
					<template #default="scope">
					</template>
				</el-table-column>
			</el-table>
			<el-pagination 
				background
				:layout="layouts.join(',')"
				:total="total" 
				:page-size="listQuery.limit"
				prev-text="上一页"
				next-text="下一页"
				:hide-on-single-page="false"
				:style='{"border":"0px solid #e1e1e1","padding":"10px 0","margin":"20px 0 0","whiteSpace":"nowrap","color":"#333","textAlign":"left","background":"none","width":"100%","fontWeight":"500"}'
				@size-change="sizeChange"
				@current-change="currentChange" 
				@prev-click="prevClick"
				@next-click="nextClick"  />
		</div>
		<formModel ref="formRef" @formModelChange="formModelChange"></formModel>
	</div>
</template>
<script setup>
	import axios from 'axios'
	import {
		reactive,
		ref,
		getCurrentInstance,
		nextTick,
		onMounted,
		watch,
	} from 'vue'
	import {
		useRoute,
		useRouter
	} from 'vue-router'
	import {
		ElMessageBox
	} from 'element-plus'
	const context = getCurrentInstance()?.appContext.config.globalProperties;
	import formModel from './formModel.vue'
	
	//基础信息
	const tableName = 'storeup'
	const formName = '我的收藏'
	const route = useRoute()
	watch(() => route.query.type,() => {
		init()
	})
	//基础信息
	onMounted(()=>{
	})
	//列表数据
	const list = ref(null)
	const table = ref(null)
	const listQuery = ref({
		page: 1,
		limit: 20,
		sort: 'id',
		order: 'desc'
	})
	const searchQuery = ref({})
	const selRows = ref([])
	const listLoading = ref(false)
	const listChange = (row) =>{
		nextTick(()=>{
			table.value.clearSelection()
			table.value.toggleRowSelection(row)
		})
	}
	//列表
	const getList = () => {
		listLoading.value = true
		let params = JSON.parse(JSON.stringify(listQuery.value))
		params['type'] = route.query.type
		params['sort'] = 'id'
		params['order'] = 'desc'
		if(searchQuery.value.name&&searchQuery.value.name!=''){
			params['name'] = '%' + searchQuery.value.name + '%'
		}
		context?.$http({
			url: `${tableName}/page`,
			method: 'get',
			params: params
		}).then(res => {
			listLoading.value = false
			list.value = res.data.data.list
			total.value = Number(res.data.data.total)
		})
	}
	//删
	const delClick = (id) => {
		let ids = ref([])
		if (id) {
			ids.value = [id]
		} else {
			if (selRows.value.length) {
				for (let x in selRows.value) {
					ids.value.push(selRows.value[x].id)
				}
			} else {
				return false
			}
		}
		ElMessageBox.confirm(`是否删除选中${formName}`, '提示', {
			confirmButtonText: '是',
			cancelButtonText: '否',
			type: 'warning',
		}).then(() => {
			context?.$http({
				url: `${tableName}/delete`,
				method: 'post',
				data: ids.value
			}).then(res => {
				context?.$toolUtil.message('删除成功', 'success',()=>{
					getList()
				})
			})
		})
	}
	//多选
	const handleSelectionChange = (e) => {
		selRows.value = e
	}
	//列表数据
	//分页
	const total = ref(0)
	const layouts = ref(["total","prev","pager","next","sizes","jumper"])
	const sizeChange = (size) => {
		listQuery.value.limit = size
		getList()
	}
	const currentChange = (page) => {
		listQuery.value.page = page
		getList()
	}
	const prevClick = () => {
		listQuery.value.page = listQuery.value.page - 1
		getList()
	}
	const nextClick = () => {
		listQuery.value.page = listQuery.value.page + 1
		getList()
	}
	//分页
	//权限验证
	const btnAuth = (e,a)=>{
		return context?.$toolUtil.isAuth(e,a)
	}
	//搜索
	const searchClick = () => {
		listQuery.value.page = 1
		getList()
	}
	//表单
	const formRef = ref(null)
	const formModelChange=()=>{
		searchClick()
	}
	const addClick = ()=>{
		formRef.value.init()
	}
	const editClick = ()=>{
		if(selRows.value.length){
			formRef.value.init(selRows.value[0].id,'edit')
		}
	}
	
	const infoClick = (id=null)=>{
		if(id){
			formRef.value.init(id,'info')
		}
		else if(selRows.value.length){
			formRef.value.init(selRows.value[0].id,'info')
		}
	}
	// 表单
	// 预览文件
	const preClick = (file) =>{
		if(!file){
			context?.$toolUtil.message('文件不存在','error')
		}
		window.open(context?.$config.url + file)
		// const a = document.createElement('a');
		// a.style.display = 'none';
		// a.setAttribute('target', '_blank');
		// file && a.setAttribute('download', file);
		// a.href = context?.$config.url + file;
		// document.body.appendChild(a);
		// a.click();
		// document.body.removeChild(a);
	}
	// 下载文件
	const download = (file) => {
		if(!file){
			context?.$toolUtil.message('文件不存在','error')
		}
		let arr = file.replace(new RegExp('file/', "g"), "")
		axios.get((location.href.split(context?.$config.name).length>1 ? location.href.split(context?.$config.name)[0] :'') + context?.$config.name + '/file/download?fileName=' + arr, {
			headers: {
				token: context?.$toolUtil.storageGet('Token')
			},
			responseType: "blob"
		}).then(({
			data
		}) => {
			const binaryData = [];
			binaryData.push(data);
			const objectUrl = window.URL.createObjectURL(new Blob(binaryData, {
				type: 'application/pdf;chartset=UTF-8'
			}))
			const a = document.createElement('a')
			a.href = objectUrl
			a.download = arr
			// a.click()
			// 下面这个写法兼容火狐
			a.dispatchEvent(new MouseEvent('click', {
				bubbles: true,
				cancelable: true,
				view: window
			}))
			window.URL.revokeObjectURL(data)
		})
	}


	//初始化
	const init = () => {
		getList()
	}
	init()
</script>
<style lang="scss" scoped>
	
	// 操作盒子
	.list_search_view {
		margin: 0 0 20px;
		display: flex;
		align-items: center;
		flex-wrap: wrap;
		// 搜索盒子
		.search_form {
			border: 0px solid #654B3C;
			border-radius: 0px;
			padding: 0 0px;
			margin: 0 30px 0 0;
			display: flex;
			align-items: center;
			order: 2;
			// 子盒子
			.search_view {
				padding: 0;
				margin: 0 0 0 0;
				display: flex;
				align-items: center;
				// 搜索label
				.search_label {
					margin: 0 5px 0;
					color: #666;
					background: none;
					font-weight: 500;
					display: inline-block;
					width: auto;
					font-size: 14px;
					line-height: 40px;
					text-align: right;
					height: 40px;
				}
				// 搜索item
				.search_box {
					padding: 0;
					display: inline-block;
					width: auto;
					// 输入框
					:deep(.search_inp) {
						border-radius: 0 0 20px 20px;
						padding: 0 10px;
						background: #fff;
						width: 150px;
						border-color: #7ca065;
						border-width: 1px;
						line-height: 40px;
						box-sizing: border-box;
						border-style: solid;
						height: 40px;
						//去掉默认样式
						.el-input__wrapper{
							border: none;
							box-shadow: none;
							background: none;
							border-radius: 0;
							height: 100%;
							padding: 0;
						}
						.is-focus {
							box-shadow: none !important;
						}
					}
				}
			}
			// 搜索按钮盒子
			.search_btn_view {
				width: 20%;
				display: flex;
				padding: 0 20px;
				// 搜索按钮
				.search_btn {
					border: 0px solid #ff9900;
					cursor: pointer;
					border-radius: 0 0 20px 20px;
					padding: 0 20px;
					margin: 0 0 0 10px;
					color: #fff;
					background: #7ca065;
					width: auto;
					font-size: 14px;
					min-width: 80px;
					height: 40px;
				}
				// 搜索按钮-悬浮
				.search_btn:hover {
					opacity: 0.8;
				}
			}
		}
		//头部按钮盒子
		.btn_view {
			margin: 10px 20px 0 0;
			display: flex;
			order: 2;
			// 其他
			:deep(.el-button--default){
				border: 0px solid #b9c8d2;
				cursor: pointer;
				padding: 0 10px;
				margin: 0 10px 10px 0;
				color: #333;
				font-weight: 600;
				font-size: 14px;
				border-radius: 0 0 20px 20px;
				box-shadow: 4px 4px 0px rgba(0,0,0,.1);
				background: rgba(42,219,203,.2);
				width: auto;
				min-width: 68px;
				height: 40px;
			}
			// 其他-悬浮
			:deep(.el-button--default:hover){
				opacity: 0.8;
			}
			// 新增
			:deep(.el-button--success){
				border: 0px solid #b9c8d2;
				cursor: pointer;
				padding: 0 8px;
				margin: 0 10px 10px 0;
				color: #7ca065;
				font-weight: 600;
				font-size: 14px;
				border-radius: 0 0 20px 20px;
				box-shadow: 4px 4px 0px rgba(0,0,0,.1);
				background: rgba(124,160,101,.2);
				width: auto;
				min-width: 68px;
				height: 40px;
			}
			// 新增-悬浮
			:deep(.el-button--success:hover){
				opacity: 0.8;
			}
			// 修改
			:deep(.el-button--primary){
				border: 0px solid #b9c8d2;
				cursor: pointer;
				padding: 0 10px;
				margin: 0 10px 10px 0;
				color: #333;
				font-weight: 600;
				font-size: 14px;
				border-radius: 0 0 20px 20px;
				box-shadow: 4px 4px 0px rgba(0,0,0,.1);
				background: rgba(42,219,203,.2);
				width: auto;
				min-width: 68px;
				height: 40px;
			}
			// 修改-悬浮
			:deep(.el-button--primary:hover){
				opacity: 0.8;
			}
			// 详情
			:deep(.el-button--info){
				border: 0px solid #b9c8d2;
				cursor: pointer;
				padding: 0 10px;
				margin: 0 10px 10px 0;
				color: #333;
				font-weight: 600;
				font-size: 14px;
				border-radius: 0 0 20px 20px;
				box-shadow: 4px 4px 0px rgba(0,0,0,.1);
				background: rgba(42,219,203,.2);
				width: auto;
				min-width: 68px;
				height: 40px;
			}
			// 详情-悬浮
			:deep(.el-button--info:hover){
				opacity: 0.8;
			}
			// 删除
			:deep(.el-button--danger){
				border: 0px solid #b9c8d2;
				cursor: pointer;
				padding: 0 10px;
				margin: 0 10px 10px 0;
				color: #d67081;
				font-weight: 600;
				font-size: 14px;
				border-radius: 0 0 20px 20px;
				box-shadow: 4px 4px 0px rgba(0,0,0,.1);
				background: rgba(223,37,67,.2);
				width: auto;
				min-width: 68px;
				height: 40px;
			}
			// 删除-悬浮
			:deep(.el-button--danger:hover){
				opacity: 0.8;
			}
			// 统计
			:deep(.el-button--warning){
				border: 0px solid #b9c8d2;
				cursor: pointer;
				border-radius: 0 0 20px 20px;
				padding: 0 10px;
				box-shadow: 4px 4px 0px rgba(0,0,0,.1);
				margin: 0 10px 10px 0;
				color: #2ce1d2;
				background: rgba(42,219,203,.2);
				font-weight: 600;
				width: auto;
				font-size: 14px;
				height: 40px;
			}
			// 统计-悬浮
			:deep(.el-button--warning:hover){
				opacity: 0.8;
			}
		}
	}
	// 表格样式
	.el-table {
		border-radius: 0px;
		padding: 0;
		background: #fff;
		width: 100%;
		font-size: 13px;
		border-color: #d2d0d0;
		border-width: 1px 0 0 1px;
		border-style: solid;
		:deep(.el-table__header-wrapper) {
			thead {
				color: inherit;
				background: url(http://codegen.caihongy.cn/20221203/1057db36d2aa4bd4b375aa0ccb752a40.png) repeat-x left top,#7ca065;
				font-weight: 500;
				width: 100%;
				tr {
					background: none;
					th {
						padding: 5px 0;
						background: none;
						border-color: #d2d0d0;
						border-width: 0 1px 1px 0;
						border-style: solid;
						text-align: left;
						.cell {
							padding: 0 5px;
							word-wrap: normal;
							color: #fff;
							white-space: normal;
							font-weight: bold;
							display: inline-block;
							vertical-align: middle;
							font-size: inherit;
							line-height: 24px;
							text-overflow: ellipsis;
							word-break: break-all;
							width: 100%;
							position: relative;
						}
					}
				}
			}
		}
		:deep(.el-table__body-wrapper) {
			tbody {
				width: 100%;
				tr {
					background: #fff;
					td {
						padding: 5px 0;
						color: #555;
						background: #fff;
						border-color: #d2d0d0;
						border-width: 0 1px 1px 0;
						border-style: solid;
						text-align: left;
						.cell {
							padding: 0 10px;
							overflow: hidden;
							word-break: break-all;
							white-space: normal;
							line-height: 24px;
							text-overflow: ellipsis;
							// 编辑
							.el-button--primary {
								border: 0px solid #eee;
								cursor: pointer;
								border-radius: 0 0 10px 10px;
								padding: 0 16px;
								box-shadow: 3px 3px 0px rgba(100,100,100,.1);
								margin: 0 6px 6px 0;
								color: #4d9be3;
								background: rgba(77,155,227,.1);
								width: auto;
								font-size: 13px;
								height: 32px;
							}
							// 编辑-悬浮
							.el-button--primary:hover {
							}
							// 详情
							.el-button--info {
								border: 0px solid #eee;
								cursor: pointer;
								border-radius: 0 0 10px 10px;
								padding: 0 16px;
								box-shadow: 3px 3px 0px rgba(100,100,100,.1);
								margin: 0 6px 6px 0;
								color: #1cac8b;
								background: rgba(24,207,165,.1);
								width: auto;
								font-size: 14px;
								height: 32px;
							}
							// 详情-悬浮
							.el-button--info:hover {
							}
							// 删除
							.el-button--danger {
								border: 0px solid #eee;
								cursor: pointer;
								border-radius: 0 0 10px 10px;
								padding: 0 16px;
								box-shadow: 3px 3px 0px rgba(100,100,100,.1);
								margin: 0 6px 6px 0;
								color: #dc333b;
								background: rgba(220,51,59,.1);
								width: auto;
								font-size: 13px;
								height: 32px;
							}
							// 删除-悬浮
							.el-button--danger:hover {
							}
							// 跨表
							.el-button--success {
								border: 0px solid #eee;
								cursor: pointer;
								border-radius: 0 0 10px 10px;
								padding: 0 16px;
								box-shadow: 3px 3px 0px rgba(100,100,100,.1);
								margin: 0 6px 6px 0;
								color: #333;
								background: rgba(77,155,227,.1);
								width: auto;
								font-size: 13px;
								height: 32px;
							}
							// 跨表-悬浮
							.el-button--success:hover {
							}
							// 操作
							.el-button--warning {
								border: 0px solid #eee;
								cursor: pointer;
								border-radius: 0 0 10px 10px;
								padding: 0 16px;
								box-shadow: 3px 3px 0px rgba(100,100,100,.1);
								margin: 0 6px 6px 0;
								color: #333;
								background: rgba(77,155,227,.1);
								width: auto;
								font-size: 13px;
								height: 32px;
							}
							// 操作-悬浮
							.el-button--warning:hover {
							}
						}
					}
				}
				tr.el-table__row--striped {
					td {
						background: rgba(238,238,238,.5);
					}
				}
				tr:hover {
					td {
						padding: 5px 0;
						color: #666;
						background: none;
						border-color: #d2d0d0;
						border-width: 0 1px 1px 0;
						border-style: solid;
						text-align: left;
					}
				}
			}
		}
	}
	// 分页器
	.el-pagination {
		// 总页码
		:deep(.el-pagination__total) {
			margin: 0 10px 0 0;
			color: #666;
			font-weight: 400;
			display: inline-block;
			vertical-align: top;
			font-size: 13px;
			line-height: 28px;
			height: 28px;
		}
		// 上一页
		:deep(.btn-prev) {
			border: 1px solid #ccc;
			border-radius: 0px;
			padding: 0 5px;
			margin: 0 5px;
			color: #666;
			background: #fff;
			display: inline-block;
			vertical-align: top;
			font-size: 13px;
			line-height: 26px;
			min-width: 35px;
			height: 26px;
		}
		// 下一页
		:deep(.btn-next) {
			border: 1px solid #ccc;
			border-radius: 0px;
			padding: 0 5px;
			margin: 0 5px;
			color: #666;
			background: #fff;
			display: inline-block;
			vertical-align: top;
			font-size: 13px;
			line-height: 26px;
			min-width: 35px;
			height: 26px;
		}
		// 上一页禁用
		:deep(.btn-prev:disabled) {
			border: 1px solid #ccc;
			cursor: not-allowed;
			border-radius: 0px;
			padding: 0 5px;
			margin: 0 5px;
			color: #666;
			background: #fff;
			display: inline-block;
			vertical-align: top;
			font-size: 13px;
			line-height: 26px;
			height: 26px;
		}
		// 下一页禁用
		:deep(.btn-next:disabled) {
			border: 1px solid #ccc;
			cursor: not-allowed;
			border-radius: 0px;
			padding: 0 5px;
			margin: 0 5px;
			color: #666;
			background: #fff;
			display: inline-block;
			vertical-align: top;
			font-size: 13px;
			line-height: 26px;
			height: 26px;
		}
		// 页码
		:deep(.el-pager) {
			padding: 0;
			margin: 0;
			display: inline-block;
			vertical-align: top;
			// 数字
			.number {
				cursor: pointer;
				border: 1px solid #ccc;
				padding: 0 4px;
				margin: 0 5px;
				color: #999;
				display: inline-block;
				vertical-align: top;
				font-size: 13px;
				line-height: 26px;
				border-radius: 0px;
				background: #fff;
				text-align: center;
				min-width: 30px;
				height: 26px;
			}
			// 数字悬浮
			.number:hover {
				cursor: pointer;
				border: 1px solid #e6e6e6;
				padding: 0 4px;
				margin: 0 5px;
				color: #fff;
				display: inline-block;
				vertical-align: top;
				font-size: 13px;
				line-height: 26px;
				border-radius: 0px;
				background: #7ca065;
				text-align: center;
				min-width: 30px;
				height: 26px;
			}
			// 选中
			.number.is-active {
				cursor: default;
				border: 1px solid #e6e6e6;
				padding: 0 4px;
				margin: 0 5px;
				color: #fff;
				display: inline-block;
				vertical-align: top;
				font-size: 13px;
				line-height: 26px;
				border-radius: 0px;
				background: #7ca065;
				text-align: center;
				min-width: 30px;
				height: 26px;
			}
		}
		// sizes
		:deep(.el-pagination__sizes) {
			display: inline-block;
			vertical-align: top;
			font-size: 13px;
			line-height: 28px;
			height: 28px;
			.el-select {
				border: 1px solid #ccc;
				cursor: pointer;
				padding: 0;
				color: #606266;
				display: inline-block;
				font-size: 13px;
				line-height: 28px;
				border-radius: 0px;
				outline: 0;
				background: #fff;
				width: 100%;
				text-align: center;
				height: 28px;
			}
		}
		// 跳页
		:deep(.el-pagination__jump) {
			margin: 0 0 0 24px;
			color: #606266;
			display: inline-block;
			vertical-align: top;
			font-size: 13px;
			line-height: 26px;
			height: 26px;
			// 输入框
			.el-input {
				border: 1px solid #ccc;
				cursor: pointer;
				border-radius: 0px;
				padding: 0 3px;
				color: #606266;
				background: #fff;
				display: inline-block;
				width: auto;
				font-size: 14px;
				line-height: 26px;
				text-align: center;
				height: 26px;
				//去掉默认样式
				.el-input__wrapper{
					border: none;
					box-shadow: none;
					background: none;
					border-radius: 0;
					height: 100%;
					padding: 0;
				}
				.is-focus {
					box-shadow: none !important;
				}
			}
		}
	}
</style>
