const config = {
    get() {
        return {
            url : process.env.VUE_APP_BASE_API_URL + process.env.VUE_APP_BASE_API + '/',
            name: process.env.VUE_APP_BASE_API,
			menuList:[
				{
					name: '论坛信息管理',
					icon: '${frontMenu.fontClass}',
					child:[
						{
							name:'交流中心',
							url:'/index/jiaoliuzhongxinList'
						},
					]
				},
				{
					name: '房屋资讯管理',
					icon: '${frontMenu.fontClass}',
					child:[
						{
							name:'房屋资讯',
							url:'/index/fangwuzixunList'
						},
					]
				},
				{
					name: '二手房交易管理',
					icon: '${frontMenu.fontClass}',
					child:[
						{
							name:'二手房信息',
							url:'/index/ershoufangxinxiList'
						},
					]
				},
			]
        }
    },
    getProjectName(){
        return {
            projectName: "二手房信息管理系统"
        } 
    }
}
export default config
