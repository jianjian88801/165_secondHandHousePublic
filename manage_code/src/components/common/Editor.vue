<template>
	<div>
		<!-- 图片上传组件辅助-->
		<div style="border: 1px solid #ccc">
			<Toolbar style="border-bottom: 1px solid #ccc" :editor="editorRef" :defaultConfig="toolbarConfig"
				:mode="mode" />
			<Editor style="height: 500px; overflow-y: hidden;" v-model="valueHtml" :defaultConfig="editorConfig"
				:mode="mode" @onCreated="handleCreated" @onChange="handleChange" />
		</div>
	</div>
</template>
<script setup>
	import '@wangeditor/editor/dist/css/style.css' // 引入 css

	import {
		onBeforeUnmount,
		ref,
		shallowRef,
		onMounted,
		getCurrentInstance,
		defineEmits,
		watch
	} from 'vue'
	const context = getCurrentInstance()?.appContext.config.globalProperties;
	import {
		Editor,
		Toolbar
	} from '@wangeditor/editor-for-vue'
	const emit = defineEmits(['change'])
	// 编辑器实例，必须用 shallowRef
	const editorRef = shallowRef()

	// 内容 HTML
	const valueHtml = ref('')
	const props = defineProps({
		value: {
			type: String,
			default: ''
		},
		placeholder: {
			type: String,
			default: '请输入'
		},
		readonly: {
			type: Boolean, // 是否只读
			default: false
		}
	})
	// 模拟 ajax 异步获取内容
	onMounted(() => {
		setTimeout(() => {
			valueHtml.value = props.value
		}, 1500)
	})
	watch(()=>props.value, (n) => {
    	valueHtml.value = n
  	});
	const mode = 'default' // 或 'simple'
	const toolbarConfig = {
		excludeKeys: ['fullScreen', 'group-video', 'insertLink', 'insertImage']
	}
	const editorConfig = {
		placeholder: props.placeholder,
		readOnly: props.readonly,
		fontSize: ['12px', '14px', '16px'],
		MENU_CONF: {
			uploadImage: {
				server: context?.$config.name + '/file/upload',
				fieldName: 'file',
				// 单个文件的最大体积限制，默认为 2M
				maximgSize: 10 * 1024 * 1024, // 10M
				// 最多可上传几个文件，默认为 100
				maxNumberOfimgs: 10,
				// 选择文件时的类型限制，默认为 ['image/*'] 。如不想限制，则设置为 []
				allowedimgTypes: ['image/*'],
				// 自定义上传参数，例如传递验证的 token 等。参数会被添加到 formData 中，一起上传到服务端。
				meta: {
					// token: 'xxx',
					// otherKey: 'yyy'
					// img:''
				},
				// 将 meta 拼接到 url 参数中，默认 false
				metaWithUrl: false,

				// 自定义增加 http  header
				headers: {
					'Token': context?.$toolUtil.storageGet('Token')
				},

				// 跨域是否传递 cookie ，默认为 false
				withCredentials: true,

				// 超时时间，默认为 10 秒
				timeout: 10 * 1000, //10 秒

				// 上传前
				onBeforeUpload(imgs) {
					context?.$toolUtil.message('图片正在上传中,请耐心等待', 'warning')
					return imgs;
				},
				// 自定义插入图片
				customInsert(res, insertFn) {
					// 因为自定义插入导致onSuccess与onFailed回调函数不起作用,自己手动处理

					if (res.code === 0) {
						context?.$toolUtil.message('图片上传成功', 'success')
					} else {
						context?.$toolUtil.message('图片上传失败，请重新尝试', 'error')
					}
					// 从 res 中找到 url alt href ，然后插入图片
					insertFn(context?.$config.url + 'file/' + res.file);
					// console.log(res, "res.data")
				},

				// 单个文件上传成功之后
				onSuccess(img, res) {},

				// 单个文件上传失败
				onFailed(img, res) {},

				// 上传进度的回调函数
				onProgress(progress) {
					// console.log('progress', progress);
					// progress 是 0-100 的数字
				},

				// 上传错误，或者触发 timeout 超时
				onError(img, err, res) {
					// console.log(`${img.name} 上传出错`, err, res);
				}
			}
		}
	}

	// 组件销毁时，也及时销毁编辑器
	onBeforeUnmount(() => {
		const editor = editorRef.value
		if (editor == null) return
		editor.destroy()
	})
	const handleChange = (e) => {
		emit('change', editorRef.value.getHtml())
	}
	const handleCreated = (editor) => {
		editorRef.value = editor // 记录 editor 实例，重要！
	}
</script>

<style>
	.editor {
		line-height: normal !important;
	}

	.ql-snow .ql-tooltip[data-mode="link"]::before {
		content: "请输入链接地址:";
	}

	.ql-snow .ql-tooltip.ql-editing a.ql-action::after {
		border-right: 0px;
		content: "保存";
		padding-right: 0px;
	}

	.ql-snow .ql-tooltip[data-mode="video"]::before {
		content: "请输入视频地址:";
	}

	.ql-container {
		height: 400px;
	}

	.ql-snow .ql-picker.ql-size .ql-picker-label::before,
	.ql-snow .ql-picker.ql-size .ql-picker-item::before {
		content: "14px";
	}

	.ql-snow .ql-picker.ql-size .ql-picker-label[data-value="small"]::before,
	.ql-snow .ql-picker.ql-size .ql-picker-item[data-value="small"]::before {
		content: "10px";
	}

	.ql-snow .ql-picker.ql-size .ql-picker-label[data-value="large"]::before,
	.ql-snow .ql-picker.ql-size .ql-picker-item[data-value="large"]::before {
		content: "18px";
	}

	.ql-snow .ql-picker.ql-size .ql-picker-label[data-value="huge"]::before,
	.ql-snow .ql-picker.ql-size .ql-picker-item[data-value="huge"]::before {
		content: "32px";
	}

	.ql-snow .ql-picker.ql-header .ql-picker-label::before,
	.ql-snow .ql-picker.ql-header .ql-picker-item::before {
		content: "文本";
	}

	.ql-snow .ql-picker.ql-header .ql-picker-label[data-value="1"]::before,
	.ql-snow .ql-picker.ql-header .ql-picker-item[data-value="1"]::before {
		content: "标题1";
	}

	.ql-snow .ql-picker.ql-header .ql-picker-label[data-value="2"]::before,
	.ql-snow .ql-picker.ql-header .ql-picker-item[data-value="2"]::before {
		content: "标题2";
	}

	.ql-snow .ql-picker.ql-header .ql-picker-label[data-value="3"]::before,
	.ql-snow .ql-picker.ql-header .ql-picker-item[data-value="3"]::before {
		content: "标题3";
	}

	.ql-snow .ql-picker.ql-header .ql-picker-label[data-value="4"]::before,
	.ql-snow .ql-picker.ql-header .ql-picker-item[data-value="4"]::before {
		content: "标题4";
	}

	.ql-snow .ql-picker.ql-header .ql-picker-label[data-value="5"]::before,
	.ql-snow .ql-picker.ql-header .ql-picker-item[data-value="5"]::before {
		content: "标题5";
	}

	.ql-snow .ql-picker.ql-header .ql-picker-label[data-value="6"]::before,
	.ql-snow .ql-picker.ql-header .ql-picker-item[data-value="6"]::before {
		content: "标题6";
	}

	.ql-snow .ql-picker.ql-font .ql-picker-label::before,
	.ql-snow .ql-picker.ql-font .ql-picker-item::before {
		content: "标准字体";
	}

	.ql-snow .ql-picker.ql-font .ql-picker-label[data-value="serif"]::before,
	.ql-snow .ql-picker.ql-font .ql-picker-item[data-value="serif"]::before {
		content: "衬线字体";
	}

	.ql-snow .ql-picker.ql-font .ql-picker-label[data-value="monospace"]::before,
	.ql-snow .ql-picker.ql-font .ql-picker-item[data-value="monospace"]::before {
		content: "等宽字体";
	}
</style>
