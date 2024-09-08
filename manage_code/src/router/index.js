	import {
		createRouter,
		createWebHashHistory
	} from 'vue-router'
	import news from '@/views/news/list'
	import luntanfenlei from '@/views/luntanfenlei/list'
	import maijia from '@/views/maijia/list'
	import fangwuhuxing from '@/views/fangwuhuxing/list'
	import dingdanxinxi from '@/views/dingdanxinxi/list'
	import discussfangwuzixun from '@/views/discussfangwuzixun/list'
	import discussershoufangxinxi from '@/views/discussershoufangxinxi/list'
	import storeup from '@/views/storeup/list'
	import users from '@/views/users/list'
	import jiaoliuzhongxin from '@/views/jiaoliuzhongxin/list'
	import yonghu from '@/views/yonghu/list'
	import fangwuzixun from '@/views/fangwuzixun/list'
	import ershoufangxinxi from '@/views/ershoufangxinxi/list'
	import zixunfenlei from '@/views/zixunfenlei/list'
	import zaixianzixun from '@/views/zaixianzixun/list'
	import config from '@/views/config/list'
	import discussjiaoliuzhongxin from '@/views/discussjiaoliuzhongxin/list'
	import maijiaRegister from '@/views/maijia/register'
	import maijiaCenter from '@/views/maijia/center'

export const routes = [{
		path: '/login',
		name: 'login',
		component: () => import('../views/login.vue')
	},{
		path: '/',
		name: '首页',
		component: () => import('../views/index'),
		children: [{
			path: '/',
			name: '首页',
			component: () => import('../views/HomeView.vue'),
			meta: {
				affix: true
			}
		}, {
			path: '/updatepassword',
			name: '修改密码',
			component: () => import('../views/updatepassword.vue')
		}
		
		,{
			path: '/maijiaCenter',
			name: '卖家个人中心',
			component: maijiaCenter
		}
		,{
			path: '/news',
			name: '公告信息',
			component: news
		}
		,{
			path: '/luntanfenlei',
			name: '论坛分类',
			component: luntanfenlei
		}
		,{
			path: '/maijia',
			name: '卖家',
			component: maijia
		}
		,{
			path: '/fangwuhuxing',
			name: '房屋户型',
			component: fangwuhuxing
		}
		,{
			path: '/dingdanxinxi',
			name: '订单信息',
			component: dingdanxinxi
		}
		,{
			path: '/discussfangwuzixun',
			name: '房屋资讯评论评论',
			component: discussfangwuzixun
		}
		,{
			path: '/discussershoufangxinxi',
			name: '二手房信息评论评论',
			component: discussershoufangxinxi
		}
		,{
			path: '/storeup',
			name: '我的收藏',
			component: storeup
		}
		,{
			path: '/users',
			name: '管理员',
			component: users
		}
		,{
			path: '/jiaoliuzhongxin',
			name: '交流中心',
			component: jiaoliuzhongxin
		}
		,{
			path: '/yonghu',
			name: '用户',
			component: yonghu
		}
		,{
			path: '/fangwuzixun',
			name: '房屋资讯',
			component: fangwuzixun
		}
		,{
			path: '/ershoufangxinxi',
			name: '二手房信息',
			component: ershoufangxinxi
		}
		,{
			path: '/zixunfenlei',
			name: '资讯分类',
			component: zixunfenlei
		}
		,{
			path: '/zaixianzixun',
			name: '在线咨询',
			component: zaixianzixun
		}
		,{
			path: '/config',
			name: '轮播图',
			component: config
		}
		,{
			path: '/discussjiaoliuzhongxin',
			name: '交流中心评论评论',
			component: discussjiaoliuzhongxin
		}
		]
	},
	{
		path: '/maijiaRegister',
		name: '卖家注册',
		component: maijiaRegister
	},
]

const router = createRouter({
	history: createWebHashHistory(process.env.BASE_URL),
	routes
})

export default router
