package com.cl.dao;

import com.cl.entity.ZaixianzixunEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.cl.entity.view.ZaixianzixunView;


/**
 * 在线咨询
 * 
 * @author 
 * @email 
 * @date 2024-04-25 10:27:10
 */
public interface ZaixianzixunDao extends BaseMapper<ZaixianzixunEntity> {
	
	List<ZaixianzixunView> selectListView(@Param("ew") Wrapper<ZaixianzixunEntity> wrapper);

	List<ZaixianzixunView> selectListView(Pagination page,@Param("ew") Wrapper<ZaixianzixunEntity> wrapper);
	
	ZaixianzixunView selectView(@Param("ew") Wrapper<ZaixianzixunEntity> wrapper);
	

}
