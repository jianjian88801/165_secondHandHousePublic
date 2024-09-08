package com.cl.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.cl.utils.PageUtils;
import com.cl.entity.DiscussjiaoliuzhongxinEntity;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import com.cl.entity.view.DiscussjiaoliuzhongxinView;


/**
 * 交流中心评论表
 *
 * @author 
 * @email 
 * @date 2024-04-25 10:27:11
 */
public interface DiscussjiaoliuzhongxinService extends IService<DiscussjiaoliuzhongxinEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<DiscussjiaoliuzhongxinView> selectListView(Wrapper<DiscussjiaoliuzhongxinEntity> wrapper);
   	
   	DiscussjiaoliuzhongxinView selectView(@Param("ew") Wrapper<DiscussjiaoliuzhongxinEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,Wrapper<DiscussjiaoliuzhongxinEntity> wrapper);
   	

}

