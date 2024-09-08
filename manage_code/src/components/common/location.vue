<template>
	<div>
		<el-dialog v-model="locationVisible" :title="'选择地点'" width="70%" destroy-on-close>

			<div class="aMapMain">
				<div v-if="isDisplayAMap">
					<div class="aMap">
						<el-amap ref="mapRef" :min-zoom="8" :max-zoom="22" :center="center" :zoom="zoom"
							@click="clickMap" @init="initMap">
							<el-amap-search-box @select="selectPoi" @choose="selectPoi" v-if="isShowAMap" />
							<el-amap-marker :position="componentMarker.position" :content="componentMarker.content" />
							<el-amap-control-geolocation @complete="getLocation" :extensions="extensions" />
						</el-amap>
					</div>
					<div class="aMapAddress">
						<p>
							<span>坐标：</span>
							<span>{{ toParentsMapInfo.lng }}</span>，
							<span>{{ toParentsMapInfo.lat }}</span>
						</p>
						<p>
							<span>地址：</span>
							<span>{{ toParentsMapInfo.fulladdress }}</span>
						</p>
					</div>
				</div>
				<div v-else class="isDisplayAMap">
					<el-amap ref="mapRef" :min-zoom="8" :max-zoom="22" :center="center" :zoom="zoom" @click="clickMap"
						@init="initMap">
						<el-amap-search-box @select="selectPoi" @choose="selectPoi" v-if="isShowAMap" />
						<el-amap-marker :position="componentMarker.position" :content="componentMarker.content" />
						<el-amap-control-geolocation @complete="getLocation" :extensions="extensions" />
					</el-amap>
				</div>
			</div>
			<template #footer>
				<span class="formModel_btn_box">
					<el-button class="formModel_confirm" @click="chooseMapClick">确定位置</el-button>
				</span>
			</template>
		</el-dialog>
	</div>
</template>

<script setup>
	import {
		Plus,
		Delete
	} from '@element-plus/icons-vue'
	import {
		toRefs
	} from '@vueuse/shared'
	import {
		reactive,
		ref
	} from '@vue/reactivity'
	import {
		defineProps,
		getCurrentInstance
	} from '@vue/runtime-core'
	import {
		defineEmits
	} from 'vue';
	const {
		proxy
	} = getCurrentInstance()
	const props = defineProps({
		toParentsMap: {
			type: Object, // 父组件传来的默认数据
			default: {}
		},
		isShowAMap: {
			type: Boolean, // 控制是否展示搜索框
			default: true
		},
		isDisplayAMap: {
			type: Boolean, // 控制是否展示地图试图
			default: true
		},
	})
	const locationVisible = ref(false)
	const mapShow = () => {
		locationVisible.value = true
	}
	//声明父级调用
	defineExpose({
		mapShow
	})
	const emit = defineEmits(['mapData'])
	const zoom = ref(16)
	const center = ref([
		props.toParentsMap.lng || 113.887902,
		props.toParentsMap.lat || 22.554732
	])
	const componentMarker = ref({
		position: [
			props.toParentsMap.lng || 113.887902,
			props.toParentsMap.lat || 22.554732
		],
		visible: true,
		draggable: false
	})
	const toParentsMapInfo = ref({})

	/** 接受父组件传值进行赋值 初始化地图数据*/
	const initMapInfo = () => {
		toParentsMapInfo.value = props.toParentsMap

	}

	/** 初始化地图*/
	const initMap = (e) => {
	}
	//获取当前位置
	const getLocation = (e) => {
		let position = [e.position.lng, e.position.lat]
		componentMarker.value.position = position
		center.value = position
		getAddress(position)
	}
	//点击描点
	const clickMap = (e) => {
		let position = [e.lnglat.lng, e.lnglat.lat]
		center.value = position
		componentMarker.value.position = position
		getAddress(position)
	}
	/** 选取定位地图*/
	const selectPoi = (e) => {
		let poi = e.poi
		if (poi.address.length > 0 && poi.location != undefined &&
			poi.location != null && poi.location != '') {
			let position = [e.poi.location.lng, e.poi.location.lat]
			center.value = position
			componentMarker.value.position = position
			getAddress(position)
		} else {
			// proxy.$modal.msgWarning('输入的位置定位失败, 请输入详细位置进行定位!')
		}
	}
	//获取详细地址
	const getAddress = (position) => {
		var geocoder = new AMap.Geocoder({
			radius: 1000,
			extensions: 'all'
		})
		geocoder.getAddress(position, function(status, result) {
			toParentsMapInfo.value = {
				lng: position[0], //经度
				lat: position[1], //纬度
				fulladdress: '' //详细地址
			}
			let reg = /.+?(省|市|自治区|自治州|盟|旗|县|区)/g // 截取地图地址
			let detailedAddress = result.regeocode.formattedAddress
			let districtList = detailedAddress.match(reg)
			if (districtList.length < 3) {
				toParentsMapInfo.value.city = districtList[0]
				toParentsMapInfo.value.zone = districtList[1]
			} else {
				toParentsMapInfo.value.city = districtList[1]
				toParentsMapInfo.value.zone = districtList[2]
			}
			toParentsMapInfo.value.province = districtList[0]
			toParentsMapInfo.value.fulladdress = detailedAddress
			
		})
	}
	const chooseMapClick=()=>{
		emit('mapData', toParentsMapInfo.value) // 传值到父组件
		locationVisible.value = false
	}
	initMapInfo()
</script>

<style lang="scss">
	.amap-sug-result {
		z-index: 2099;
	}

	.amap-copyright {
		height: 24px;
	}

	.el-vue-search-box-container,
	.el-vue-search-box-container input {
		height: 29px;
		border-radius: 8px;
	}

	.aMapMain {
		width: 100%;

		.aMap {
			width: 100%;
			height: 500px;
		}

		.aMapAddress {
			text-align: left;

			p {
				margin-top: 5px;
				margin-bottom: 0;
			}
		}

		.isDisplayAMap {
			width: 100%;
			height: 36px;

			.el-vue-amap {
				display: none;
			}

			.el-vue-search-box-container {
				position: initial;
				width: 100%;
			}
		}
	}

	.el-vue-search-box-container {
		border: 1px solid #e2e2e2;
		height: 36px;
		box-shadow: none;
	}
</style>
