<template>
	<el-dialog title="拍照上传" v-model="visible" width="1065px">
		<div class="box">
			<video id="videoCamera" class="canvas" :width="videoWidth" :height="videoHeight" autoPlay></video>
			<canvas id="canvasCamera" class="canvas" :width="videoWidth" :height="videoHeight"></canvas>
		</div>
		<template #footer>
			<div class="formModel_btn_box">
				<el-upload class="formModel_confirm" :action="getActionUrl" :on-success="uploadSuccess" :show-file-list="false"
					accept=".jpg,.png,.jpge" style="margin-right:10px">
					<el-button size="small">
						上传图片
					</el-button>
				</el-upload>
				<el-button class="formModel_confirm" @click="drawImage" size="small">
					拍照
				</el-button>
				<el-button class="formModel_confirm" v-if="os" @click="getCompetence" size="small">
					打开摄像头
				</el-button>
				<el-button class="formModel_cancel" v-else @click="stopNavigator" size="small">
					关闭摄像头
				</el-button>
				<el-button class="formModel_confirm" @click="resetCanvas" size="small">
					重置
				</el-button>
				<el-button class="formModel_cancel" @click="onCancel" size="small">
					完成
				</el-button>
			</div>

		</template>
	</el-dialog>
</template>
<script setup>
	import {
		nextTick,
		ref,
		getCurrentInstance,
		computed,
		defineEmits
	} from 'vue';
	import axios from "axios";
	const context = getCurrentInstance()?.appContext.config.globalProperties;
	const visible = ref(false) //弹窗
	const loading = ref(false) //上传按钮加载
	const os = ref(false) //控制摄像头开关
	const thisVideo = ref(null)
	const thisContext = ref(null)
	const thisCancas = ref(null)
	const videoWidth = ref(500)
	const videoHeight = ref(400)
	const postOptions = ref([])
	const CertCtl = ref('')
	const imgSrc = ref('')
	const emit = defineEmits(['imgChange'])
	const getActionUrl = computed(() => {
		return context?.$config.url + 'file/upload'
	})

	const uploadSuccess = (e) => {
		visible.value = false;
		emit('imgChange', e.file)
	}
	// 调用摄像头权限
	const getCompetence = () => {
		//必须在model中render后才可获取到dom节点,直接获取无法获取到model中的dom节点
		nextTick(() => {
			os.value = false; //切换成关闭摄像头
			thisCancas.value = document.getElementById('canvasCamera');
			thisContext.value = document.getElementById('canvasCamera').getContext('2d');
			thisVideo.value = document.getElementById('videoCamera');
			// 旧版本浏览器可能根本不支持mediaDevices，我们首先设置一个空对象
			if (navigator.mediaDevices === undefined) {
				navigator.menavigatordiaDevices = {}
			}
			// 一些浏览器实现了部分mediaDevices，我们不能只分配一个对象
			// 使用getUserMedia，因为它会覆盖现有的属性。
			// 这里，如果缺少getUserMedia属性，就添加它。
			if (navigator.mediaDevices.getUserMedia === undefined) {
				navigator.mediaDevices.getUserMedia = function(constraints) {
					// 首先获取现存的getUserMedia(如果存在)
					let getUserMedia = navigator.webkitGetUserMedia || navigator.mozGetUserMedia ||
						navigator
						.getUserMedia;
					// 有些浏览器不支持，会返回错误信息
					// 保持接口一致
					if (!getUserMedia) {
						return Promise.reject(new Error('getUserMedia is not implemented in this browser'))
					}
					// 否则，使用Promise将调用包装到旧的navigator.getUserMedia
					return new Promise(function(resolve, reject) {
						getUserMedia.call(navigator, constraints, resolve, reject)
					})
				}
			}
			const constraints = {
				audio: false,
				video: {
					width: videoWidth.value,
					height: videoHeight.value,
					transform: 'scaleX(-1)'
				}
			};
			navigator.mediaDevices.getUserMedia(constraints).then(function(stream) {
				// 旧的浏览器可能没有srcObject
				if ('srcObject' in thisVideo.value) {
					thisVideo.value.srcObject = stream
				} else {
					// 避免在新的浏览器中使用它，因为它正在被弃用。
					thisVideo.value.src = window.URL.createObjectURL(stream)
				}
				thisVideo.value.onloadedmetadata = function(e) {
					thisVideo.value.play()
				}
			}).catch(err => {
				context?.$toolUtil.notify('警告', '没有开启摄像头权限或浏览器版本不兼容.', 'warning')
			});
		});
	}
	/*调用摄像头拍照开始*/
	const onTake = () => {
		visible.value = true
		getCompetence()
	}

	//声明父级调用
	defineExpose({
		onTake
	})
	const base64toFile = (dataurl, filename = 'file') => {
		let arr = dataurl.split(',')
		let mime = arr[0].match(/:(.*?);/)[1]
		let suffix = mime.split('/')[1]
		let bstr = atob(arr[1])
		let n = bstr.length
		let u8arr = new Uint8Array(n)
		while (n--) {
			u8arr[n] = bstr.charCodeAt(n)
		}
		let file = new File([u8arr], `${filename}.${suffix}`, {
			type: mime
		})
		return file
	}
	const uploadImg = () => {
		let param = new FormData()
		param.append('file', base64toFile(imgSrc.value))
		axios.post(getActionUrl.value,param,{
			headers:{
				'Content-Type':'multipart/form-data'
			}
		}).then(res=>{
			emit('imgChange', res.data.file)
		})
	}
	const onCancel = () => {
		uploadImg()
		visible.value = false
		/* this.resetCanvas();*/
		stopNavigator()
	}
	const drawImage = () => {
		// 点击，canvas画图
		thisContext.value.drawImage(thisVideo.value, 0, 0, videoWidth.value, videoHeight.value);
		// 获取图片base64链接
		imgSrc.value = thisCancas.value.toDataURL('image/png');
		/*const imgSrc=this.imgSrc;*/
	}
	//清空画布
	const clearCanvas = (id) => {
		let c = document.getElementById(id);
		let cxt = c.getContext("2d");
		cxt.clearRect(0, 0, c.width, c.height);
	}
	//重置画布
	const resetCanvas = () => {
		imgSrc.value = "";
		clearCanvas('canvasCamera');
	}
	//关闭摄像头
	const stopNavigator = () => {
		if (thisVideo.value && thisVideo.value !== null) {
			thisVideo.value.srcObject.getTracks()[0].stop();
			os.value = true; //切换成打开摄像头
		}
	}
</script>
<style rel="stylesheet/scss" lang="scss" scoped>
	.avatar-uploader .el-upload {
		border: 1px dashed #ccc;
		border-radius: 6px;
		cursor: pointer;
		position: relative;
		overflow: hidden;
	}

	.avatar-uploader .el-upload:hover {
		border-color: #409EFF;
	}

	.avatar-uploader-icon {
		font-size: 28px;
		color: #8c939d;
		width: 178px;
		height: 178px;
		line-height: 178px;
		text-align: center;
		border: 1px dashed #ccc;
	}

	.avatar {
		width: 178px;
		height: 178px;
		display: block;
	}
</style>