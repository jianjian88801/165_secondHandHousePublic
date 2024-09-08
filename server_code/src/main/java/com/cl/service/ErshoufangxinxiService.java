package com.cl.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.cl.utils.PageUtils;
import com.cl.entity.ErshoufangxinxiEntity;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import com.cl.entity.view.ErshoufangxinxiView;


/**
 * 二手房信息
 *
 * @author 
 * @email 
 * @date 2024-04-25 10:27:10
 */
public interface ErshoufangxinxiService extends IService<ErshoufangxinxiEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<ErshoufangxinxiView> selectListView(Wrapper<ErshoufangxinxiEntity> wrapper);
   	
   	ErshoufangxinxiView selectView(@Param("ew") Wrapper<ErshoufangxinxiEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,Wrapper<ErshoufangxinxiEntity> wrapper);
   	

}

