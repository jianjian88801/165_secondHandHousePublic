package com.cl.dao;

import com.cl.entity.DiscussershoufangxinxiEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.cl.entity.view.DiscussershoufangxinxiView;


/**
 * 二手房信息评论表
 * 
 * @author 
 * @email 
 * @date 2024-04-25 10:27:11
 */
public interface DiscussershoufangxinxiDao extends BaseMapper<DiscussershoufangxinxiEntity> {
	
	List<DiscussershoufangxinxiView> selectListView(@Param("ew") Wrapper<DiscussershoufangxinxiEntity> wrapper);

	List<DiscussershoufangxinxiView> selectListView(Pagination page,@Param("ew") Wrapper<DiscussershoufangxinxiEntity> wrapper);
	
	DiscussershoufangxinxiView selectView(@Param("ew") Wrapper<DiscussershoufangxinxiEntity> wrapper);
	

}
