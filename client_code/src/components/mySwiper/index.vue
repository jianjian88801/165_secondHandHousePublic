<template>
	<div style="width: 100%">
		<swiper class="swiper" :loop="loop" :modules="swiperModules" :navigation="navigation"
			:pagination="paginationEnd" :scrollbar="scrollbarEnd" :space-between="spaceBetween"
			:auto-height="autoHeight" :slides-per-view="slidesPerView" :centered-slides="centeredSlides"
			:free-mode="freeMode" :effect="effectEnd" :coverflow-effect="coverflowEffect" :cube-effect="cubeEffect"
			:creative-effect="creativeEffect" :direction="direction" :autoplay="autoplayEnd" :grid="gridEnd">
			<swiper-slide class="slide" v-for="(item,index) in data" :key="index">
				<el-image v-if="type==1" style="width: 100%;" :src="item.value?$config.url + item.value:''"></el-image>
				<el-image v-if="type==2" style="width: 100%;" :src="item?$config.url + item:''"></el-image>
				<slot :row="item" :index="index" v-if="type==3"></slot>
			</swiper-slide>
		</swiper>
	</div>
</template>
<script setup>
	import {
		Swiper,
		SwiperSlide
	} from 'swiper/vue'
	import {
		Navigation,
		Pagination,
		Scrollbar,
		FreeMode,
		EffectFade,
		EffectCube,
		EffectCreative,
		EffectFlip,
		EffectCoverflow,
		Autoplay,
		Grid
	} from 'swiper'
	import {
		ref,
		toRefs
	} from 'vue';
	import 'swiper/css'
	import 'swiper/css/navigation'
	import 'swiper/css/pagination'
	import 'swiper/css/scrollbar'
	import 'swiper/css/free-mode'
	import 'swiper/css/effect-fade'
	import 'swiper/css/effect-coverflow'
	import 'swiper/css/effect-cube'
	import 'swiper/css/effect-flip'
	import 'swiper/css/effect-creative'
	import 'swiper/css/grid'
	const props = defineProps({
		type: {
			type: Number,
			default: 1
		},
		//循环播放
		loop: {
			type: Boolean,
			default: false
		},
		//左右按钮
		navigation: {
			type: Boolean,
			default: true
		},
		//底部点
		pagination: {
			type: Boolean,
			default: true
		},
		//滚动条
		scrollbar: {
			type: Boolean,
			default: false
		},
		//同时显示几张图 
		slidesPerView: {
			type: Number,
			default: 3
		},
		//间隔 
		spaceBetween: {
			type: Number,
			default: 30
		},
		//自动高度
		autoHeight: {
			type: Boolean,
			default: false
		},
		//橱窗模式 //与loop不适合一起用
		centeredSlides: {
			type: Boolean,
			default: false
		},
		//自由模式
		freeMode: {
			type: Boolean,
			default: false
		},
		//底部点类型 1、默认。2、动态。3、进度条。4、数字。5、圆圈数字。
		paginationType: {
			type: Number,
			default: 1
		},
		//效果 0、默认。1、褪色。2、3d覆盖。3、3d立方体。4、3d翻转。5、切牌。6、缩小切换。7、替换。8、上下翻转切换。9、滚动切换。10、翻书
		effectType: {
			type: Number,
			default: 0
		},
		data: {
			type: Array,
			default: []
		},
		//横向竖向
		direction: {
			type: String,
			default: 'horizontal'
		},
		//自动播放
		autoplay: {
			type: Boolean,
			default: false
		},
		slidesPerColumn: {
			type: Number,
			default: 1
		}
	})
	const swiperModules = ref([])
	//多种底部点类型
	const paginationEnd = ref(null)
	const bulletRenderer = (index, className) => {
		const _className = `${className} swiper-pagination-bullet-custom`
		return `<span class="${_className}">${index + 1}</span>`
	}
	//滚动条
	const scrollbarEnd = ref(false)
	const {
		type,
		loop,
		navigation,
		pagination,
		scrollbar,
		paginationType,
		spaceBetween,
		autoHeight,
		slidesPerView,
		centeredSlides,
		freeMode,
		effectType,
		data,
		direction,
		autoplay,
		slidesPerColumn
	} = toRefs(props)
	const init = () => {
		//是否展示左右按钮
		navigationChange()
		//底部点类型
		paginationChange()
		//滚动条
		scrollbarChange()
		//自由模式
		freeModeChange()
		//切换动画
		effectTypeChange()
		//自动播放
		autoplayChange()
		// 网格状
		gridChange()
	}
	//是否展示左右按钮
	const navigationChange = () => {
		if (navigation.value) {
			swiperModules.value.push(Navigation)
		}
	}
	//底部点类型
	const paginationChange = () => {
		if (pagination.value) {
			swiperModules.value.push(Pagination)
			if (paginationType.value == 1) {
				paginationEnd.value = true
			} else if (paginationType.value == 2) {
				paginationEnd.value = {
					clickable: true,
					dynamicBullets: true
				}
			} else if (paginationType.value == 3) {
				paginationEnd.value = {
					type: 'progressbar'
				}
			} else if (paginationType.value == 4) {
				paginationEnd.value = {
					type: 'fraction'
				}
			} else if (paginationType.value == 5) {
				paginationEnd.value = {
					clickable: true,
					renderBullet: bulletRenderer
				}
			}
		} else {
			paginationEnd.value = false
		}
	}
	//滚动条
	const scrollbarChange = () => {
		if (scrollbar.value) {
			swiperModules.value.push(Scrollbar)
			scrollbarEnd.value = {
				hide: true
			}
		}
	}
	//自由模式
	const freeModeChange = () => {
		if (freeMode.value) {
			swiperModules.value.push(FreeMode)
		}
	}
	//切换动画
	const effectEnd = ref(false)
	const coverflowEffect = ref(false)
	const cubeEffect = ref(false)
	const creativeEffect = ref(false)
	const effectTypeChange = () => {
		if (effectType.value == 1) {
			effectEnd.value = 'fade'
			swiperModules.value.push(EffectFade)
		} else if (effectType.value == 2) {
			effectEnd.value = 'coverflow'
			swiperModules.value.push(EffectCoverflow)
			coverflowEffect.value = {
				rotate: 50,
				stretch: 0,
				depth: 100,
				modifier: 1,
				slideShadows: true
			}
		} else if (effectType.value == 3) {
			effectEnd.value = 'cube'
			swiperModules.value.push(EffectCube)
			cubeEffect.value = {
				shadow: true,
				slideShadows: true,
				shadowOffset: 20,
				shadowScale: 0.94
			}
		} else if (effectType.value == 4) {
			effectEnd.value = 'flip'
			swiperModules.value.push(EffectFlip)
		} else if (effectType.value == 5) {
			effectEnd.value = 'creative'
			swiperModules.value.push(EffectCreative)
			creativeEffect.value = {
				prev: {
					shadow: true,
					translate: [0, 0, -400]
				},
				next: {
					translate: ['100%', 0, 0]
				}
			}
		} else if (effectType.value == 6) {
			effectEnd.value = 'creative'
			swiperModules.value.push(EffectCreative)
			creativeEffect.value = {
				prev: {
					shadow: true,
					translate: ['-120%', 0, -500]
				},
				next: {
					shadow: true,
					translate: ['120%', 0, -500]
				}
			}
		} else if (effectType.value == 7) {
			effectEnd.value = 'creative'
			swiperModules.value.push(EffectCreative)
			creativeEffect.value = {
				prev: {
					shadow: true,
					translate: ['-20%', 0, -1]
				},
				next: {
					translate: ['100%', 0, 0]
				}
			}
		} else if (effectType.value == 8) {
			effectEnd.value = 'creative'
			swiperModules.value.push(EffectCreative)
			creativeEffect.value = {
				prev: {
					shadow: true,
					translate: [0, 0, -800],
					rotate: [180, 0, 0]
				},
				next: {
					shadow: true,
					translate: [0, 0, -800],
					rotate: [-180, 0, 0]
				}
			}
		} else if (effectType.value == 9) {
			effectEnd.value = 'creative'
			swiperModules.value.push(EffectCreative)
			creativeEffect.value = {
				prev: {
					shadow: true,
					translate: ['-125%', 0, -800],
					rotate: [0, 0, -90]
				},
				next: {
					shadow: true,
					translate: ['125%', 0, -800],
					rotate: [0, 0, 90]
				}
			}
		} else if (effectType.value == 10) {
			effectEnd.value = 'creative'
			swiperModules.value.push(EffectCreative)
			creativeEffect.value = {
				prev: {
					shadow: true,
					origin: 'left center',
					translate: ['-5%', 0, -200],
					rotate: [0, 100, 0]
				},
				next: {
					origin: 'right center',
					translate: ['5%', 0, -200],
					rotate: [0, -100, 0]
				}
			}
		}
	}
	//自动播放
	const autoplayEnd = ref(false)
	const autoplayChange = () => {
		if (autoplay.value) {
			autoplayEnd.value = {
				delay: 2500,
				disableOnInteraction: false
			}
			swiperModules.value.push(Autoplay)
		}
	}
	const gridEnd = ref({
		rows:1,
		fill:'row'
	})
	const gridChange = () => {
		gridEnd.value.rows = slidesPerColumn.value
		console.log(gridEnd.value)
		swiperModules.value.push(Grid)
	}
	init()
</script>

<style lang="scss" scoped>
	.swiper {
		width: 100%;

		.slide {
			display: flex;
			align-items: center;
			justify-content: center;
		}
	}
</style>