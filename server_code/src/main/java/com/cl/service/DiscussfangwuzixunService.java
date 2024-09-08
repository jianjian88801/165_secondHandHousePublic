package com.cl.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.cl.utils.PageUtils;
import com.cl.entity.DiscussfangwuzixunEntity;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import com.cl.entity.view.DiscussfangwuzixunView;


/**
 * 房屋资讯评论表
 *
 * @author 
 * @email 
 * @date 2024-04-25 10:27:11
 */
public interface DiscussfangwuzixunService extends IService<DiscussfangwuzixunEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<DiscussfangwuzixunView> selectListView(Wrapper<DiscussfangwuzixunEntity> wrapper);
   	
   	DiscussfangwuzixunView selectView(@Param("ew") Wrapper<DiscussfangwuzixunEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,Wrapper<DiscussfangwuzixunEntity> wrapper);
   	

}

