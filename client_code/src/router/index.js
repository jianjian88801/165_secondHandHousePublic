import { createRouter, createWebHashHistory } from 'vue-router'
import index from '../views'
import home from '../views/pages/home.vue'
import login from '../views/pages/login.vue'
import newsList from '@/views/pages/news/list'
import yonghuList from '@/views/pages/yonghu/list'
import yonghuDetail from '@/views/pages/yonghu/formModel'
import yonghuAdd from '@/views/pages/yonghu/formAdd'
import yonghuRegister from '@/views/pages/yonghu/register'
import yonghuCenter from '@/views/pages/yonghu/center'
import maijiaList from '@/views/pages/maijia/list'
import maijiaDetail from '@/views/pages/maijia/formModel'
import maijiaAdd from '@/views/pages/maijia/formAdd'
import fangwuhuxingList from '@/views/pages/fangwuhuxing/list'
import fangwuhuxingDetail from '@/views/pages/fangwuhuxing/formModel'
import fangwuhuxingAdd from '@/views/pages/fangwuhuxing/formAdd'
import ershoufangxinxiList from '@/views/pages/ershoufangxinxi/list'
import ershoufangxinxiDetail from '@/views/pages/ershoufangxinxi/formModel'
import ershoufangxinxiAdd from '@/views/pages/ershoufangxinxi/formAdd'
import dingdanxinxiList from '@/views/pages/dingdanxinxi/list'
import dingdanxinxiDetail from '@/views/pages/dingdanxinxi/formModel'
import dingdanxinxiAdd from '@/views/pages/dingdanxinxi/formAdd'
import storeupList from '@/views/pages/storeup/list'
import zaixianzixunList from '@/views/pages/zaixianzixun/list'
import zaixianzixunDetail from '@/views/pages/zaixianzixun/formModel'
import zaixianzixunAdd from '@/views/pages/zaixianzixun/formAdd'
import jiaoliuzhongxinList from '@/views/pages/jiaoliuzhongxin/list'
import jiaoliuzhongxinDetail from '@/views/pages/jiaoliuzhongxin/formModel'
import jiaoliuzhongxinAdd from '@/views/pages/jiaoliuzhongxin/formAdd'
import luntanfenleiList from '@/views/pages/luntanfenlei/list'
import luntanfenleiDetail from '@/views/pages/luntanfenlei/formModel'
import luntanfenleiAdd from '@/views/pages/luntanfenlei/formAdd'
import zixunfenleiList from '@/views/pages/zixunfenlei/list'
import zixunfenleiDetail from '@/views/pages/zixunfenlei/formModel'
import zixunfenleiAdd from '@/views/pages/zixunfenlei/formAdd'
import fangwuzixunList from '@/views/pages/fangwuzixun/list'
import fangwuzixunDetail from '@/views/pages/fangwuzixun/formModel'
import fangwuzixunAdd from '@/views/pages/fangwuzixun/formAdd'

const routes = [{
		path: '/',
		redirect: '/index/home'
	},
	{
		path: '/index',
		component: index,
		children: [{
			path: 'home',
			component: home
		}
		, {
			path: 'newsList',
			component: newsList
		}
		, {
			path: 'yonghuList',
			component: yonghuList
		}, {
			path: 'yonghuDetail',
			component: yonghuDetail
		}, {
			path: 'yonghuAdd',
			component: yonghuAdd
		}
		, {
			path: 'yonghuCenter',
			component: yonghuCenter
		}
		, {
			path: 'maijiaList',
			component: maijiaList
		}, {
			path: 'maijiaDetail',
			component: maijiaDetail
		}, {
			path: 'maijiaAdd',
			component: maijiaAdd
		}
		, {
			path: 'fangwuhuxingList',
			component: fangwuhuxingList
		}, {
			path: 'fangwuhuxingDetail',
			component: fangwuhuxingDetail
		}, {
			path: 'fangwuhuxingAdd',
			component: fangwuhuxingAdd
		}
		, {
			path: 'ershoufangxinxiList',
			component: ershoufangxinxiList
		}, {
			path: 'ershoufangxinxiDetail',
			component: ershoufangxinxiDetail
		}, {
			path: 'ershoufangxinxiAdd',
			component: ershoufangxinxiAdd
		}
		, {
			path: 'dingdanxinxiList',
			component: dingdanxinxiList
		}, {
			path: 'dingdanxinxiDetail',
			component: dingdanxinxiDetail
		}, {
			path: 'dingdanxinxiAdd',
			component: dingdanxinxiAdd
		}
		, {
			path: 'storeupList',
			component: storeupList
		}
		, {
			path: 'zaixianzixunList',
			component: zaixianzixunList
		}, {
			path: 'zaixianzixunDetail',
			component: zaixianzixunDetail
		}, {
			path: 'zaixianzixunAdd',
			component: zaixianzixunAdd
		}
		, {
			path: 'jiaoliuzhongxinList',
			component: jiaoliuzhongxinList
		}, {
			path: 'jiaoliuzhongxinDetail',
			component: jiaoliuzhongxinDetail
		}, {
			path: 'jiaoliuzhongxinAdd',
			component: jiaoliuzhongxinAdd
		}
		, {
			path: 'luntanfenleiList',
			component: luntanfenleiList
		}, {
			path: 'luntanfenleiDetail',
			component: luntanfenleiDetail
		}, {
			path: 'luntanfenleiAdd',
			component: luntanfenleiAdd
		}
		, {
			path: 'zixunfenleiList',
			component: zixunfenleiList
		}, {
			path: 'zixunfenleiDetail',
			component: zixunfenleiDetail
		}, {
			path: 'zixunfenleiAdd',
			component: zixunfenleiAdd
		}
		, {
			path: 'fangwuzixunList',
			component: fangwuzixunList
		}, {
			path: 'fangwuzixunDetail',
			component: fangwuzixunDetail
		}, {
			path: 'fangwuzixunAdd',
			component: fangwuzixunAdd
		}
		]
	},
	{
		path: '/login',
		component: login
	}
	,{
		path: '/yonghuRegister',
		component: yonghuRegister
	}
]

const router = createRouter({
  history: createWebHashHistory(process.env.BASE_URL),
  routes
})

export default router
