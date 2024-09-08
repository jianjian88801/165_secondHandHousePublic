<template>
	<div>
		<el-upload v-if="type=='img'" :action="uploadUrl" list-type="picture-card" :limit="limit" :multiple="multiple"
			v-model:file-list="fileList" :on-success="uploadSuccess" :on-exceed="uploadExceed" :disabled="disabled"
			:headers="uploadHeaders" :class="{disabled:disabled||fileList.length==limit}">
			<el-icon>
				<Plus />
			</el-icon>
			<template #file="{ file }">
				<div style="width: 100%;height: 100%;">
					<img class="el-upload-list__item-thumbnail" :src="file.url" alt="" style="object-fit: cover;" />
					<span class="el-upload-list__item-actions">
						<span class="el-upload-list__item-preview" @click="handlePictureCardPreview(file)">
							<el-icon>
								<zoom-in />
							</el-icon>
						</span>
						<span v-if="!disabled" class="el-upload-list__item-delete" @click="handleRemove(file)">
							<el-icon>
								<Delete />
							</el-icon>
						</span>
					</span>
				</div>
			</template>
			<template #tip>
				<div class="el-upload__tip">
					{{tips}}
				</div>
			</template>
		</el-upload>
		<el-upload v-else class="upload-demo" drag :action="uploadUrl" :headers="uploadHeaders" :limit="limit"
			:multiple="multiple" v-model:file-list="fileList" :on-preview="uploadPreview" multiple
			:on-success="uploadSuccess" :on-error="uploadError" :on-exceed="uploadExceed" :on-remove="handleRemove">
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
		<el-dialog v-model="dialogVisible" width="60%">
			<img w-full :src="dialogImageUrl" alt="Preview Image" style="width: 100%;" />
		</el-dialog>
	</div>
</template>

<script setup>
	import {
		ref,
		getCurrentInstance,
		toRefs,
		defineEmits,
		watch
	} from 'vue'
	const context = getCurrentInstance()?.appContext.config.globalProperties;
	const emit = defineEmits(['change'])
	//props
	const props = defineProps({
		action: String,
		limit: {
			type: Number,
			default: 3
		},
		multiple: {
			type: Boolean,
			default: false
		},
		fileUrls: String,
		tip: {
			type: String,
			default: '请上传'
		},
		type: {
			type: String,
			default: 'img'
		},
		disabled: {
			type: Boolean,
			default: false
		}
	})
	//data
	const tips = ref('请上传')
	const uploadUrl = ref('')
	const fileList = ref([])
	const fileUrlList = ref([])
	const {
		action,
		fileUrls,
		limit,
		multiple,
		tip,
		type,
		disabled
	} = toRefs(props)
	const dialogImageUrl = ref('')
	const dialogVisible = ref(false)
	const uploadHeaders = ref({})

	watch(fileUrls, () => {
		init()
	})
	// // methods

	//移除
	const handleRemove = (e) => {
		fileUrlList.value.splice(e.uid - 1, 1)
		fileUrlsChange(fileUrlList.value)
	}
	//查看图片
	const handlePictureCardPreview = (e) => {
		dialogImageUrl.value = e.url
		dialogVisible.value = true
	}
	//成功回调
	const uploadSuccess = (e, file, fileList) => {
		fileUrlList.value.push("file/" + e.file)
		fileUrlsChange(fileUrlList.value)
	}
	const uploadError = (e) => {
		context?.$toolUtil.message('上传失败', 'error')
	}
	const uploadPreview = (e) => {
		window.open(e.url)
	}
	//处理上传图片
	const fileUrlsChange = (list) => {
		var token = context?.$toolUtil.storageGet("token");
		var list1 = []
		var list2 = []
		list.forEach(function(item, index) {
			var url = item.split("?")[0];
			var url1 = ''
			if (!url.startsWith("http")) {
				url1 = context?.$config.url + url
			}
			var name = index + 1;
			var file = {
				name: name,
				url: url1 + "?token=" + token,
				uid: index + 1
			};
			list1.push(file);
			list2.push(url);
		});
		fileList.value = list1;
		fileUrlList.value = list2;
		emit('change', fileUrlList.value.join(','))
	}
	//超出数量
	const uploadExceed = () => {
		context?.$toolUtil.message(`最多上传${limit.value}个文件`, 'error')
	}
	//created
	const init = () => {
		console.log(fileUrls.value)
		uploadUrl.value = context?.$config.name + '/' + action.value
		uploadHeaders.value = {
			'Token': context?.$toolUtil.storageGet("Token")
		}
		if (tip.value) {
			tips.value = tip.value
		}
		let list = []
		if (fileUrls.value) {
			list = fileUrls.value.split(',')
		}
		fileUrlsChange(list)
	}
	init()
</script>

<style>
	.el-upload__tip {
		display: flex;
		align-items: center;
		justify-content: flex-start;
	}
  .disabled .el-upload--picture-card {
    display: none!important;
  }
</style>