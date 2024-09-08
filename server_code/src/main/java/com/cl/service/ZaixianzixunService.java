package com.cl.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.cl.utils.PageUtils;
import com.cl.entity.ZaixianzixunEntity;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import com.cl.entity.view.ZaixianzixunView;


/**
 * 在线咨询
 *
 * @author 
 * @email 
 * @date 2024-04-25 10:27:10
 */
public interface ZaixianzixunService extends IService<ZaixianzixunEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<ZaixianzixunView> selectListView(Wrapper<ZaixianzixunEntity> wrapper);
   	
   	ZaixianzixunView selectView(@Param("ew") Wrapper<ZaixianzixunEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,Wrapper<ZaixianzixunEntity> wrapper);
   	

}

