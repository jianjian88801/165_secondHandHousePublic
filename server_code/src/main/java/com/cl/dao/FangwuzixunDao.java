package com.cl.dao;

import com.cl.entity.FangwuzixunEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.cl.entity.view.FangwuzixunView;


/**
 * 房屋资讯
 * 
 * @author 
 * @email 
 * @date 2024-04-25 10:27:11
 */
public interface FangwuzixunDao extends BaseMapper<FangwuzixunEntity> {
	
	List<FangwuzixunView> selectListView(@Param("ew") Wrapper<FangwuzixunEntity> wrapper);

	List<FangwuzixunView> selectListView(Pagination page,@Param("ew") Wrapper<FangwuzixunEntity> wrapper);
	
	FangwuzixunView selectView(@Param("ew") Wrapper<FangwuzixunEntity> wrapper);
	

}
