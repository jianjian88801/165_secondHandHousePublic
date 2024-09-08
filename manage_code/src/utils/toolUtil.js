import {
	ElMessage,
	ElMessageBox,
	ElNotification
} from 'element-plus'
import menu from './menu';
import CryptoJS from './cryptojs.js'
let KEY = '1234567890123456'
let IV = 'abcdefghijklmnop'
const toolUtil = {
	//提示语
	message(msg, type, close = null) {
		ElMessage({
			message: msg,
			type: type,
			duration: 2500,
			onClose() {
				if (close) {
					close()
				}
			}
		})
	},
	//右部提示语
	notify(title, msg, type = 'success', close = null) {
		ElNotification({
			title: title,
			message: msg,
			type: type,
			onClose() {
				if (close) {
					close()
				}
			}
		})
	},
	storageSet(key, value) {
		localStorage.setItem(key, value);
	},
	storageGet(key) {
		return localStorage.getItem(key) ? localStorage.getItem(key) : "";
	},
	storageGetObj(key) {
		return localStorage.getItem(key) ? JSON.parse(localStorage.getItem(key)) : null;
	},
	storageRemove(key) {
		localStorage.removeItem(key);
	},
	storageClear() {

		localStorage.removeItem('Token');
		localStorage.removeItem('role');
		localStorage.removeItem('sessionTable');
		localStorage.removeItem('adminName');
	},
	/**
	 * 邮箱
	 * 
	 */
	isEmail(s) {
		return /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((.[a-zA-Z0-9_-]{2,3}){1,2})$/.test(s)
	},
	/**
	 * 手机号码
	 * 
	 */
	isMobile(s) {
		return /^(0|86|17951)?(13[0-9]|15[012356789]|16[6]|19[89]|17[01345678]|18[0-9]|14[579]|19[0-35-9])[0-9]{8}$/.test(s)
	},
	/**
	 * 电话号码
	 * 
	 */
	isPhone(s) {
		return /^([0-9]{3,4}-)?[0-9]{7,8}$/.test(s)
	},
	/**
	 * URL地址
	 * 
	 */
	isURL(s) {
		return /^http[s]?:\/\/.*/.test(s)
	},
	/**
	 * 匹配数字，可以是小数，不可以是负数,可以为空
	 *  
	 */
	isNumber(s) {
		return /(^-?[+-]?([0-9]*\.?[0-9]+|[0-9]+\.?[0-9]*)([eE][+-]?[0-9]+)?$)|(^$)/.test(s);
	},
	/**
	 * 匹配整数，可以为空
	 *  
	 */
	isIntNumer(s) {
		return /(^-?\d+$)|(^$)/.test(s);
	},
	/**
	 * 身份证校验
	 */
	checkIdCard(idcard) {
		const regIdCard = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
		if (!regIdCard.test(idcard)) {
			return false;
		} else {
			return true;
		}
	},
	/**
	 * 是否有权限
	 * @param {*} key
	 */
	isAuth(tableName, key,) {
		let role = toolUtil.storageGet("role");
		if (!role) {
			role = '管理员';
		}
		let menus = menu.list();
		if(menus && menus.length) {
			for (let i = 0; i < menus.length; i++) {
				if (menus[i].roleName == role) {
					if(menus[i].backMenu && menus[i].backMenu.length) {
						for (let j = 0; j < menus[i].backMenu.length; j++) {
							if(menus[i].backMenu[j].child && menus[i].backMenu[j].child.length) {
								for (let k = 0; k < menus[i].backMenu[j].child.length; k++) {
									if(tableName.split('/').length>1){
										if (tableName.split('/')[0] == menus[i].backMenu[j].child[k].tableName &&tableName.split('/')[1] == menus[i].backMenu[j].child[k].menuJump) {
											return menus[i].backMenu[j].child[k].buttons.includes(key)
										}
									}else{
										if(tableName=='orders'&&menus[i].backMenu[j].child[k].tableName=='orders'&&!menus[i].backMenu[j].child[k].menuJump){
											return menus[i].backMenu[j].child[k].buttons.includes(key)
										}
										if (tableName!='orders'&&tableName == menus[i].backMenu[j].child[k].tableName) {
											return menus[i].backMenu[j].child[k].buttons.includes(key)
										}
									}
									
								}
							}
						}
					}
				}
			}
		}
		return false;
	},
	/**
	 * 获取当前时间（yyyy-MM-dd hh:mm:ss）
	 */
	getCurDateTime() {
		let currentTime = new Date(),
			year = currentTime.getFullYear(),
			month = currentTime.getMonth() + 1 < 10 ? '0' + (currentTime.getMonth() + 1) : currentTime.getMonth() +
			1,
			day = currentTime.getDate() < 10 ? '0' + currentTime.getDate() : currentTime.getDate(),
			hour = currentTime.getHours()<10 ? '0' + currentTime.getHours():currentTime.getHours(),
			minute = currentTime.getMinutes()<10? '0' + currentTime.getMinutes():currentTime.getMinutes(),
			second = currentTime.getSeconds()<10? '0' + currentTime.getSeconds():currentTime.getSeconds();
		return year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;
	},
	/**
	 *  获取当前日期（yyyy-MM-dd）
	 */
	getCurDate() {
		let currentTime = new Date(),
			year = currentTime.getFullYear(),
			month = currentTime.getMonth() + 1 < 10 ? '0' + (currentTime.getMonth() + 1) : currentTime.getMonth() +
			1,
			day = currentTime.getDate() < 10 ? '0' + currentTime.getDate() : currentTime.getDate();
		return year + "-" + month + "-" + day;
	},
	encryptDes(message) {
		const keyHex = CryptoJS.enc.Utf8.parse(KEY);
		const encrypted = CryptoJS.DES.encrypt(message, keyHex, {
			mode: CryptoJS.mode.ECB,
			padding: CryptoJS.pad.Pkcs7
		});
		return encrypted.toString();
	},
	decryptDes(ciphertext) {
		const keyHex = CryptoJS.enc.Utf8.parse(KEY);
		// direct decrypt ciphertext
		const decrypted = CryptoJS.DES.decrypt({
			ciphertext: CryptoJS.enc.Base64.parse(ciphertext)
		}, keyHex, {
			mode: CryptoJS.mode.ECB,
			padding: CryptoJS.pad.Pkcs7
		});
		return decrypted.toString(CryptoJS.enc.Utf8);
	},
	encryptAes(msg) {
		let cmode = CryptoJS.mode.CBC;
		let cpad = CryptoJS.pad.Pkcs7;
		let ciphertext = CryptoJS.AES.encrypt(msg, CryptoJS.enc.Utf8.parse(KEY), {
			mode: cmode,
			padding: cpad,
			iv: CryptoJS.enc.Utf8.parse(IV)
		}).toString();
		return ciphertext;
	},
	decryptAes(msg) {
		let cmode = CryptoJS.mode.CBC;
		let cpad = CryptoJS.pad.Pkcs7;
		var bytes = CryptoJS.AES.decrypt(msg, CryptoJS.enc.Utf8.parse(KEY), {
			mode: cmode,
			padding: cpad,
			iv: CryptoJS.enc.Utf8.parse(IV)
		});
		var originText = bytes.toString(CryptoJS.enc.Utf8);
		return originText;
	}
}
export default toolUtil;
