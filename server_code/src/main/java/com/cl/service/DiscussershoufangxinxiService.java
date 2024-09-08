package com.cl.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.cl.utils.PageUtils;
import com.cl.entity.DiscussershoufangxinxiEntity;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import com.cl.entity.view.DiscussershoufangxinxiView;


/**
 * 二手房信息评论表
 *
 * @author 
 * @email 
 * @date 2024-04-25 10:27:11
 */
public interface DiscussershoufangxinxiService extends IService<DiscussershoufangxinxiEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<DiscussershoufangxinxiView> selectListView(Wrapper<DiscussershoufangxinxiEntity> wrapper);
   	
   	DiscussershoufangxinxiView selectView(@Param("ew") Wrapper<DiscussershoufangxinxiEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,Wrapper<DiscussershoufangxinxiEntity> wrapper);
   	

}

