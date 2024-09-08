package com.cl.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.cl.utils.PageUtils;
import com.cl.entity.FangwuzixunEntity;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import com.cl.entity.view.FangwuzixunView;


/**
 * 房屋资讯
 *
 * @author 
 * @email 
 * @date 2024-04-25 10:27:11
 */
public interface FangwuzixunService extends IService<FangwuzixunEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<FangwuzixunView> selectListView(Wrapper<FangwuzixunEntity> wrapper);
   	
   	FangwuzixunView selectView(@Param("ew") Wrapper<FangwuzixunEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,Wrapper<FangwuzixunEntity> wrapper);
   	

}

