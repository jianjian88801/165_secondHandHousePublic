package com.cl.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.List;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cl.utils.PageUtils;
import com.cl.utils.Query;


import com.cl.dao.ZixunfenleiDao;
import com.cl.entity.ZixunfenleiEntity;
import com.cl.service.ZixunfenleiService;
import com.cl.entity.view.ZixunfenleiView;

@Service("zixunfenleiService")
public class ZixunfenleiServiceImpl extends ServiceImpl<ZixunfenleiDao, ZixunfenleiEntity> implements ZixunfenleiService {
	

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<ZixunfenleiEntity> page = this.selectPage(
                new Query<ZixunfenleiEntity>(params).getPage(),
                new EntityWrapper<ZixunfenleiEntity>()
        );
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, Wrapper<ZixunfenleiEntity> wrapper) {
		  Page<ZixunfenleiView> page =new Query<ZixunfenleiView>(params).getPage();
	        page.setRecords(baseMapper.selectListView(page,wrapper));
	    	PageUtils pageUtil = new PageUtils(page);
	    	return pageUtil;
 	}
    
	@Override
	public List<ZixunfenleiView> selectListView(Wrapper<ZixunfenleiEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public ZixunfenleiView selectView(Wrapper<ZixunfenleiEntity> wrapper) {
		return baseMapper.selectView(wrapper);
	}


}
