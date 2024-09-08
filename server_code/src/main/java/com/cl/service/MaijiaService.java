package com.cl.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.cl.utils.PageUtils;
import com.cl.entity.MaijiaEntity;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import com.cl.entity.view.MaijiaView;


/**
 * 卖家
 *
 * @author 
 * @email 
 * @date 2024-04-25 10:27:09
 */
public interface MaijiaService extends IService<MaijiaEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<MaijiaView> selectListView(Wrapper<MaijiaEntity> wrapper);
   	
   	MaijiaView selectView(@Param("ew") Wrapper<MaijiaEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,Wrapper<MaijiaEntity> wrapper);
   	

}

