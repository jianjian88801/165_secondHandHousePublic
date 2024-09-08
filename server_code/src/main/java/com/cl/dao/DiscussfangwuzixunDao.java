package com.cl.dao;

import com.cl.entity.DiscussfangwuzixunEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.cl.entity.view.DiscussfangwuzixunView;


/**
 * 房屋资讯评论表
 * 
 * @author 
 * @email 
 * @date 2024-04-25 10:27:11
 */
public interface DiscussfangwuzixunDao extends BaseMapper<DiscussfangwuzixunEntity> {
	
	List<DiscussfangwuzixunView> selectListView(@Param("ew") Wrapper<DiscussfangwuzixunEntity> wrapper);

	List<DiscussfangwuzixunView> selectListView(Pagination page,@Param("ew") Wrapper<DiscussfangwuzixunEntity> wrapper);
	
	DiscussfangwuzixunView selectView(@Param("ew") Wrapper<DiscussfangwuzixunEntity> wrapper);
	

}
