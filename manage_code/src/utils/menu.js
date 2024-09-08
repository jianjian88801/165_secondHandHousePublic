import toolUtil from '@/utils/toolUtil'
const menu = {
	list() {
		if(toolUtil.storageGet("menus")) {
		    return eval('(' + toolUtil.storageGet("menus")+ ')');
		} else {
		    return null;
		}
	}
}
export default menu;