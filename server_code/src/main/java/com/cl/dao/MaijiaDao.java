package com.cl.dao;

import com.cl.entity.MaijiaEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.cl.entity.view.MaijiaView;


/**
 * 卖家
 * 
 * @author 
 * @email 
 * @date 2024-04-25 10:27:09
 */
public interface MaijiaDao extends BaseMapper<MaijiaEntity> {
	
	List<MaijiaView> selectListView(@Param("ew") Wrapper<MaijiaEntity> wrapper);

	List<MaijiaView> selectListView(Pagination page,@Param("ew") Wrapper<MaijiaEntity> wrapper);
	
	MaijiaView selectView(@Param("ew") Wrapper<MaijiaEntity> wrapper);
	

}
