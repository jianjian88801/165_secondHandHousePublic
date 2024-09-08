package com.cl.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.cl.utils.PageUtils;
import com.cl.entity.ZixunfenleiEntity;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import com.cl.entity.view.ZixunfenleiView;


/**
 * 资讯分类
 *
 * @author 
 * @email 
 * @date 2024-04-25 10:27:11
 */
public interface ZixunfenleiService extends IService<ZixunfenleiEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<ZixunfenleiView> selectListView(Wrapper<ZixunfenleiEntity> wrapper);
   	
   	ZixunfenleiView selectView(@Param("ew") Wrapper<ZixunfenleiEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,Wrapper<ZixunfenleiEntity> wrapper);
   	

}

