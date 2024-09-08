<template>
	<div>
		<el-dialog v-model="formVisible" :title="formTitle" width="50%" destroy-on-close :fullscreen='false'>
			<el-form class="formModel_form" ref="formRef" :model="form" label-width="$template2.back.add.form.base.labelWidth" :rules="rules">
				<el-row>
					<el-col :span="12">
						<el-form-item label="房屋名称" prop="fangwumingcheng">
							<el-input class="list_inp" v-model="form.fangwumingcheng" placeholder="房屋名称"
								 type="text" 								:readonly="!isAdd||disabledForm.fangwumingcheng?true:false" />
						</el-form-item>
					</el-col>

					<el-col :span="12">
						<el-form-item label="房屋面积" prop="fangwumianji">
							<el-input class="list_inp" v-model="form.fangwumianji" placeholder="房屋面积"
								 type="text" 								:readonly="!isAdd||disabledForm.fangwumianji?true:false" />
						</el-form-item>
					</el-col>

					<el-col :span="12">
						<el-form-item label="房屋地址" prop="fangwudizhi">
							<el-input class="list_inp" v-model="form.fangwudizhi" placeholder="房屋地址"
								 type="text" 								:readonly="!isAdd||disabledForm.fangwudizhi?true:false" />
						</el-form-item>
					</el-col>

					<el-col :span="12">
						<el-form-item label="房屋价格" prop="fangwujiage">
							<el-input class="list_inp" v-model.number="form.fangwujiage" placeholder="房屋价格"
								 type="number" 								:readonly="!isAdd||disabledForm.fangwujiage?true:false" />
						</el-form-item>
					</el-col>

					<el-col :span="12">
						<el-form-item label="卖家账号" prop="maijiazhanghao">
							<el-input class="list_inp" v-model="form.maijiazhanghao" placeholder="卖家账号"
								 type="text" 								:readonly="!isAdd||disabledForm.maijiazhanghao?true:false" />
						</el-form-item>
					</el-col>

					<el-col :span="12">
						<el-form-item label="卖家姓名" prop="maijiaxingming">
							<el-input class="list_inp" v-model="form.maijiaxingming" placeholder="卖家姓名"
								 type="text" 								:readonly="!isAdd||disabledForm.maijiaxingming?true:false" />
						</el-form-item>
					</el-col>

					<el-col :span="12">
						<el-form-item label="卖家电话" prop="maijiadianhua">
							<el-input class="list_inp" v-model="form.maijiadianhua" placeholder="卖家电话"
								 type="text" 								:readonly="!isAdd||disabledForm.maijiadianhua?true:false" />
						</el-form-item>
					</el-col>

					<el-col :span="12">
						<el-form-item label="用户账号" prop="yonghuzhanghao">
							<el-input class="list_inp" v-model="form.yonghuzhanghao" placeholder="用户账号"
								 type="text" 								:readonly="!isAdd||disabledForm.yonghuzhanghao?true:false" />
						</el-form-item>
					</el-col>

					<el-col :span="12">
						<el-form-item label="用户姓名" prop="yonghuxingming">
							<el-input class="list_inp" v-model="form.yonghuxingming" placeholder="用户姓名"
								 type="text" 								:readonly="!isAdd||disabledForm.yonghuxingming?true:false" />
						</el-form-item>
					</el-col>

					<el-col :span="24">
						<el-form-item label="咨询内容" prop="zixunneirong">
							<el-input v-model="form.zixunneirong" placeholder="咨询内容" type="textarea"
							:readonly="!isAdd||disabledForm.zixunneirong?true:false"
							/>
						</el-form-item>
					</el-col>
				</el-row>
			</el-form>
			<template #footer v-if="isAdd||type=='logistics'||type=='reply'">
				<span class="formModel_btn_box">
					<el-button class="formModel_cancel" @click="closeClick">取消</el-button>
					<el-button class="formModel_confirm" type="primary" @click="save"
						>
						提交
					</el-button>
				</span>
			</template>
		</el-dialog>
	</div>
</template>
<script setup>
	import {
		reactive,
		ref,
		getCurrentInstance,
		nextTick,
		computed,
		defineEmits
	} from 'vue'
	const context = getCurrentInstance()?.appContext.config.globalProperties;	
	const emit = defineEmits(['formModelChange'])
	//基础信息
	const tableName = 'zaixianzixun'
	const formName = '在线咨询'
	//基础信息
	//form表单
	const form = ref({})
	const disabledForm = ref({
		fangwumingcheng : false,
		fangwumianji : false,
		fangwudizhi : false,
		fangwujiage : false,
		maijiazhanghao : false,
		maijiaxingming : false,
		maijiadianhua : false,
		yonghuzhanghao : false,
		yonghuxingming : false,
		zixunneirong : false,
		shhf : false,
	})
	const formVisible = ref(false)
	const isAdd = ref(false)
	const formTitle = ref('')
	//表单验证
	//匹配整数
	const validateIntNumber = (rule, value, callback) => {
		if (!value) {
			callback();
		} else if (!context?.$toolUtil.isIntNumer(value)) {
			callback(new Error("请输入整数"));
		} else {
			callback();
		}
	}
	//匹配数字
	const validateNumber = (rule, value, callback) => {
		if(!value){
			callback();
		} else if (!context?.$toolUtil.isNumber(value)) {
			callback(new Error("请输入数字"));
		} else {
			callback();
		}
	}
	//匹配手机号码
	const validateMobile = (rule, value, callback) => {
		if(!value){
			callback();
		} else if (!context?.$toolUtil.isMobile(value)) {
			callback(new Error("请输入正确的手机号码"));
		} else {
			callback();
		}
	}
	//匹配电话号码
	const validatePhone = (rule, value, callback) => {
		if(!value){
			callback();
		} else if (!context?.$toolUtil.isPhone(value)) {
			callback(new Error("请输入正确的电话号码"));
		} else {
			callback();
		}
	}
	//匹配邮箱
	const validateEmail = (rule, value, callback) => {
		if(!value){
			callback();
		} else if (!context?.$toolUtil.isEmail(value)) {
			callback(new Error("请输入正确的邮箱地址"));
		} else {
			callback();
		}
	}
	//匹配身份证
	const validateIdCard = (rule, value, callback) => {
		if(!value){
			callback();
		} else if (!context?.$toolUtil.checkIdCard(value)) {
			callback(new Error("请输入正确的身份证号码"));
		} else {
			callback();
		}
	}
	//匹配网站地址
	const validateUrl = (rule, value, callback) => {
		if(!value){
			callback();
		} else if (!context?.$toolUtil.isURL(value)) {
			callback(new Error("请输入正确的URL地址"));
		} else {
			callback();
		}
	}
	const rules = ref({
		fangwumingcheng: [
			{required: true,message: '请输入',trigger: 'blur'}, 
		],
		fangwumianji: [
		],
		fangwudizhi: [
		],
		fangwujiage: [
			{required: true,message: '请输入',trigger: 'blur'}, 
			{ validator: validateNumber, trigger: 'blur' },
		],
		maijiazhanghao: [
		],
		maijiaxingming: [
		],
		maijiadianhua: [
		],
		yonghuzhanghao: [
		],
		yonghuxingming: [
		],
		zixunneirong: [
		],
		shhf: [
		],
	})
	//表单验证
	
	const formRef = ref(null)
	const id = ref(0)
	const type = ref('')
	//methods

	//获取唯一标识
	const getUUID =()=> {
      return new Date().getTime();
    }
	//重置
	const resetForm = () => {
		form.value = {
			fangwumingcheng: '',
			fangwumianji: '',
			fangwudizhi: '',
			fangwujiage: '',
			maijiazhanghao: '',
			maijiaxingming: '',
			maijiadianhua: '',
			yonghuzhanghao: '',
			yonghuxingming: '',
			zixunneirong: '',
			shhf: '',
		}
	}
	//获取info
	const getInfo = ()=>{
		context?.$http({
			url: `${tableName}/info/${id.value}`,
			method: 'get'
		}).then(res => {
			let reg=new RegExp('../../../file','g')
			form.value = res.data.data
			formVisible.value = true
		})
	}
	const crossRow = ref('')
	const crossTable = ref('')
	const crossTips = ref('')
	const crossColumnName = ref('')
	const crossColumnValue = ref('')
	//初始化
	const init=(formId=null,formType='add',formNames='',row=null,table=null,statusColumnName=null,tips=null,statusColumnValue=null)=>{
		resetForm()
		if(formId){
			id.value = formId
			type.value = formType
		}
		if(formType == 'add'){
			isAdd.value = true
			formTitle.value = '新增' + formName
			formVisible.value = true
		}else if(formType == 'info'){
			isAdd.value = false
			formTitle.value = '查看' + formName
			getInfo()
		}else if(formType == 'edit'){
			isAdd.value = true
			formTitle.value = '修改' + formName
			getInfo()
		}
		else if(formType == 'cross'){
			isAdd.value = true
			formTitle.value = formNames
			// getInfo()
			for(let x in row){
				if(x=='fangwumingcheng'){
					form.value.fangwumingcheng = row[x];
					disabledForm.value.fangwumingcheng = true;
					continue;
				}
				if(x=='fangwumianji'){
					form.value.fangwumianji = row[x];
					disabledForm.value.fangwumianji = true;
					continue;
				}
				if(x=='fangwudizhi'){
					form.value.fangwudizhi = row[x];
					disabledForm.value.fangwudizhi = true;
					continue;
				}
				if(x=='fangwujiage'){
					form.value.fangwujiage = row[x];
					disabledForm.value.fangwujiage = true;
					continue;
				}
				if(x=='maijiazhanghao'){
					form.value.maijiazhanghao = row[x];
					disabledForm.value.maijiazhanghao = true;
					continue;
				}
				if(x=='maijiaxingming'){
					form.value.maijiaxingming = row[x];
					disabledForm.value.maijiaxingming = true;
					continue;
				}
				if(x=='maijiadianhua'){
					form.value.maijiadianhua = row[x];
					disabledForm.value.maijiadianhua = true;
					continue;
				}
				if(x=='yonghuzhanghao'){
					form.value.yonghuzhanghao = row[x];
					disabledForm.value.yonghuzhanghao = true;
					continue;
				}
				if(x=='yonghuxingming'){
					form.value.yonghuxingming = row[x];
					disabledForm.value.yonghuxingming = true;
					continue;
				}
				if(x=='zixunneirong'){
					form.value.zixunneirong = row[x];
					disabledForm.value.zixunneirong = true;
					continue;
				}
			}
			if(row){
				crossRow.value = row
			}
			if(table){
				crossTable.value = table
			}
			if(tips){
				crossTips.value = tips
			}
			if(statusColumnName){
				crossColumnName.value = statusColumnName
			}
			if(statusColumnValue){
				crossColumnValue.value = statusColumnValue
			}
			formVisible.value = true
		}

		context?.$http({
			url: `${context?.$toolUtil.storageGet('sessionTable')}/session`,
			method: 'get'
		}).then(res => {
			var json = res.data.data
			if(json.hasOwnProperty('yonghuzhanghao')&& context?.$toolUtil.storageGet("role")!="管理员"){
				form.value.yonghuzhanghao = json.yonghuzhanghao
				disabledForm.value.yonghuzhanghao = true;
			}
			if(json.hasOwnProperty('yonghuxingming')&& context?.$toolUtil.storageGet("role")!="管理员"){
				form.value.yonghuxingming = json.yonghuxingming
				disabledForm.value.yonghuxingming = true;
			}
		})
	}
	//初始化
	//声明父级调用
	defineExpose({
		init
	})
	//关闭
	const closeClick = () => {
		formVisible.value = false
	}
	//富文本
	const editorChange = (e,name) =>{
		form.value[name] = e
	}
	//提交
	const save=()=>{
		var table = crossTable.value
		var objcross = JSON.parse(JSON.stringify(crossRow.value))
		let crossUserId = ''
		let crossRefId = ''
		let crossOptNum = ''
		if(type.value == 'cross'){
			if(crossColumnName.value!=''){
				if(!crossColumnName.value.startsWith('[')){
					for(let o in objcross){
						if(o == crossColumnName.value){
							objcross[o] = crossColumnValue.value
						}
					}
					//修改跨表数据
					changeCrossData(objcross)
				}else{
					crossUserId = context?.$toolUtil.storageGet('userid')
					crossRefId = objcross['id']
					crossOptNum = crossColumnName.value.replace(/\[/,"").replace(/\]/,"")
				}
			}
		}
		formRef.value.validate((valid)=>{
			if(valid){
				if(crossUserId&&crossRefId){
					form.value.crossuserid = crossUserId
					form.value.crossrefid = crossRefId
					let params = {
						page: 1,
						limit: 1000, 
						crossuserid:form.value.crossuserid,
						crossrefid:form.value.crossrefid,
					}
					context?.$http({
						url: `${tableName}/page`,
						method: 'get', 
						params: params 
					}).then(res=>{
						if(res.data.data.total>=crossOptNum){
							context?.$toolUtil.message(`${crossTips.value}`,'error')
							return false
						}else{
							context?.$http({
								url: `${tableName}/${!form.value.id ? "save" : "update"}`,
								method: 'post', 
								data: form.value 
							}).then(res=>{
								emit('formModelChange')
								context?.$toolUtil.message(`操作成功`,'success',()=>{
									formVisible.value = false
								})
							})
						}
					})
				}else{
					context?.$http({
						url: `${tableName}/${!form.value.id ? "save" : "update"}`,
						method: 'post', 
						data: form.value 
					}).then(res=>{
						emit('formModelChange')
						context?.$toolUtil.message(`操作成功`,'success',()=>{
							formVisible.value = false
						})
					})
				}
			}
		})
	}
	//修改跨表数据
	const changeCrossData=(row)=>{
		context?.$http({
			url: `${crossTable.value}/update`,
			method: 'post',
			data: row
		}).then(res=>{})
	}
</script>
<style lang="scss" scoped>
	// 表单
	.formModel_form{
		border: 0px solid #ddd;
		border-radius: 4px;
		padding: 30px;
		margin: 0;
		background: #fff;
		// form item
		:deep(.el-form-item) {
			margin: 0 150px 20px 0;
			background: none;
			display: flex;
			//label
			.el-form-item__label {
			 background: none;
			 font-weight: 500;
			 display: block;
			 width: 150px;
			 min-width: 150px;
			 text-align: right;
			}
			// 内容盒子
			.el-form-item__content {
				display: flex;
				width: calc(100% - 120px);
				justify-content: flex-start;
				align-items: center;
				flex-wrap: wrap;
				// 输入框
				.list_inp {
					border: 1px solid #7da06630;
					border-radius: 0 0 20px 20px;
					padding: 0 10px;
					background: #7da06610;
					width: auto;
					line-height: 36px;
					box-sizing: border-box;
					min-width: 250px;
					height: 36px;
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
				// 长文本
				.el-textarea__inner {
					border: 1px solid #7da06630;
					border-radius: 0 0 20px 20px;
					padding: 12px 24px;
					color: #333;
					background: #7da06610;
					width: auto;
					font-size: 14px;
					min-height: 120px;
					min-width: 400px;
				}
			}
		}
	}
	// 按钮盒子
	.formModel_btn_box {
		padding: 10px 0 20px 150px;
		display: flex;
		width: 100%;
		align-items: center;
		.formModel_cancel {
			border: 0;
			cursor: pointer;
			border-radius: 0 0 20px 20px;
			padding: 0 20px;
			margin: 0 20px 0 0;
			color: #fff;
			background: #b0a59a;
			width: auto;
			font-size: 16px;
			min-width: 100px;
			height: 50px;
		}
		.formModel_cancel:hover {
		}
		
		.formModel_confirm {
			border: 0;
			cursor: pointer;
			border-radius: 0 0 20px 20px;
			padding: 0 20px;
			margin: 0 20px 0 0;
			color: #fff;
			background: #7ca065;
			width: auto;
			font-size: 16px;
			min-width: 100px;
			height: 50px;
		}
		.formModel_confirm:hover {
		}
	}
</style>