<template>
	<el-container>
		<index-main></index-main>
	</el-container>
</template>
<script>
	import indexMain from '@/components/index/indexMain'
	export default {
		name:'indexView',
		components: {
			indexMain
		}
	}
</script>

<style lang="scss" scoped>
	// 铺满全屏
	.el-container {
		position: absolute;
		width: 100%;
		top: 0;
		left: 0;
		bottom: 0;
		display: block;
	}
</style>
