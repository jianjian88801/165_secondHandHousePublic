package com.cl.dao;

import com.cl.entity.ErshoufangxinxiEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.cl.entity.view.ErshoufangxinxiView;


/**
 * 二手房信息
 * 
 * @author 
 * @email 
 * @date 2024-04-25 10:27:10
 */
public interface ErshoufangxinxiDao extends BaseMapper<ErshoufangxinxiEntity> {
	
	List<ErshoufangxinxiView> selectListView(@Param("ew") Wrapper<ErshoufangxinxiEntity> wrapper);

	List<ErshoufangxinxiView> selectListView(Pagination page,@Param("ew") Wrapper<ErshoufangxinxiEntity> wrapper);
	
	ErshoufangxinxiView selectView(@Param("ew") Wrapper<ErshoufangxinxiEntity> wrapper);
	

}
