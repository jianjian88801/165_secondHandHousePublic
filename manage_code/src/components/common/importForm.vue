<template>
	<div>
		<el-dialog v-model="importVisible" :title="'导入'" width="70%" destroy-on-close>
			<el-form ref="ruleFormRef" :model="importForm" label-width="120px">
				<el-row>
					<el-col :span="24">
						<el-upload class="upload-demo" drag accept=".xls,.xlsx" :action="uploadUrl" :headers="uploadHeaders"
							multiple :on-success="uploadSuccess" :on-error="uploadError">
							<el-icon class="el-icon--upload">
								<upload-filled />
							</el-icon>
							<div class="el-upload__text">
								将文件拖到此处，或<em>点击上传</em>
							</div>
							<template #tip>
							  <div class="el-upload__tip">
							    {{tips}}
							  </div>
							</template>
						</el-upload>
						
					</el-col>

				</el-row>
			</el-form>
			<template #footer>
				<span class="formModel_btn_box">
					<el-button class="formModel_cancel" @click="importVisible=false">关闭</el-button>
				</span>
			</template>
		</el-dialog>
	</div>
</template>

<script setup>
	import {
		ref,
		toRefs,
		getCurrentInstance,
		defineEmits
	} from 'vue';
	const emit = defineEmits(['importChange'])
	const context = getCurrentInstance()?.appContext.config.globalProperties;
	
	//props

	const props = defineProps({
		tableName: String,
		action: String,
		tip:String
	})
	const {
		tableName,
		action,
		tip
	} = toRefs(props)
	//props
	//data
	const importVisible = ref(false)
	const importForm = ref({})
	const uploadHeaders = ref({})
	const uploadUrl = ref('')
	const tips = ref('')
	const importClick=()=>{
		init()
		importVisible.value = true
	}
	//声明父级调用
	defineExpose({
		importClick
	})
	const uploadSuccess=()=>{
		context?.$toolUtil.message('导入成功','success',obj=>{
			emit('importChange')
			importVisible.value = false
		})
	}
	const uploadError=(e)=>{
		context?.$toolUtil.message('导入失败','error')
	}
	const init = () => {
		uploadUrl.value = context?.$config.name + '/' + `${tableName.value}/importExcel`
		uploadHeaders.value = {
			'Token': context?.$toolUtil.storageGet("Token")
		}
		if (tip.value) {
			tips.value = tip.value
		}
		console.log(tips.value)
	}
	
</script>

<style>
</style>
