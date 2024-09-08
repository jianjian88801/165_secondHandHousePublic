package com.cl.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.cl.utils.PageUtils;
import com.cl.entity.JiaoliuzhongxinEntity;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import com.cl.entity.view.JiaoliuzhongxinView;


/**
 * 交流中心
 *
 * @author 
 * @email 
 * @date 2024-04-25 10:27:10
 */
public interface JiaoliuzhongxinService extends IService<JiaoliuzhongxinEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<JiaoliuzhongxinView> selectListView(Wrapper<JiaoliuzhongxinEntity> wrapper);
   	
   	JiaoliuzhongxinView selectView(@Param("ew") Wrapper<JiaoliuzhongxinEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,Wrapper<JiaoliuzhongxinEntity> wrapper);
   	

}

