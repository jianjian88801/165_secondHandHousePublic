package com.cl.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.cl.utils.PageUtils;
import com.cl.entity.LuntanfenleiEntity;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import com.cl.entity.view.LuntanfenleiView;


/**
 * 论坛分类
 *
 * @author 
 * @email 
 * @date 2024-04-25 10:27:10
 */
public interface LuntanfenleiService extends IService<LuntanfenleiEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<LuntanfenleiView> selectListView(Wrapper<LuntanfenleiEntity> wrapper);
   	
   	LuntanfenleiView selectView(@Param("ew") Wrapper<LuntanfenleiEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,Wrapper<LuntanfenleiEntity> wrapper);
   	

}

