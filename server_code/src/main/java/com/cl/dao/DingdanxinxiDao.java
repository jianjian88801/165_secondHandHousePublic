package com.cl.dao;

import com.cl.entity.DingdanxinxiEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.cl.entity.view.DingdanxinxiView;


/**
 * 订单信息
 * 
 * @author 
 * @email 
 * @date 2024-04-25 10:27:10
 */
public interface DingdanxinxiDao extends BaseMapper<DingdanxinxiEntity> {
	
	List<DingdanxinxiView> selectListView(@Param("ew") Wrapper<DingdanxinxiEntity> wrapper);

	List<DingdanxinxiView> selectListView(Pagination page,@Param("ew") Wrapper<DingdanxinxiEntity> wrapper);
	
	DingdanxinxiView selectView(@Param("ew") Wrapper<DingdanxinxiEntity> wrapper);
	

}
