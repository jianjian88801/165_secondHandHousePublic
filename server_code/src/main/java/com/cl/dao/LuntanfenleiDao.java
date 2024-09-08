package com.cl.dao;

import com.cl.entity.LuntanfenleiEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.cl.entity.view.LuntanfenleiView;


/**
 * 论坛分类
 * 
 * @author 
 * @email 
 * @date 2024-04-25 10:27:10
 */
public interface LuntanfenleiDao extends BaseMapper<LuntanfenleiEntity> {
	
	List<LuntanfenleiView> selectListView(@Param("ew") Wrapper<LuntanfenleiEntity> wrapper);

	List<LuntanfenleiView> selectListView(Pagination page,@Param("ew") Wrapper<LuntanfenleiEntity> wrapper);
	
	LuntanfenleiView selectView(@Param("ew") Wrapper<LuntanfenleiEntity> wrapper);
	

}
