package com.cl.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.cl.utils.PageUtils;
import com.cl.entity.FangwuhuxingEntity;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import com.cl.entity.view.FangwuhuxingView;


/**
 * 房屋户型
 *
 * @author 
 * @email 
 * @date 2024-04-25 10:27:10
 */
public interface FangwuhuxingService extends IService<FangwuhuxingEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<FangwuhuxingView> selectListView(Wrapper<FangwuhuxingEntity> wrapper);
   	
   	FangwuhuxingView selectView(@Param("ew") Wrapper<FangwuhuxingEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,Wrapper<FangwuhuxingEntity> wrapper);
   	

}

