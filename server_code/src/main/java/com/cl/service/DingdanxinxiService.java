package com.cl.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.cl.utils.PageUtils;
import com.cl.entity.DingdanxinxiEntity;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import com.cl.entity.view.DingdanxinxiView;


/**
 * 订单信息
 *
 * @author 
 * @email 
 * @date 2024-04-25 10:27:10
 */
public interface DingdanxinxiService extends IService<DingdanxinxiEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<DingdanxinxiView> selectListView(Wrapper<DingdanxinxiEntity> wrapper);
   	
   	DingdanxinxiView selectView(@Param("ew") Wrapper<DingdanxinxiEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,Wrapper<DingdanxinxiEntity> wrapper);
   	

}

