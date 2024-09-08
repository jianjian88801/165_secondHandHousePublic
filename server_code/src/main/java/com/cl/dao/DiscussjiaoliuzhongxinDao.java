package com.cl.dao;

import com.cl.entity.DiscussjiaoliuzhongxinEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.cl.entity.view.DiscussjiaoliuzhongxinView;


/**
 * 交流中心评论表
 * 
 * @author 
 * @email 
 * @date 2024-04-25 10:27:11
 */
public interface DiscussjiaoliuzhongxinDao extends BaseMapper<DiscussjiaoliuzhongxinEntity> {
	
	List<DiscussjiaoliuzhongxinView> selectListView(@Param("ew") Wrapper<DiscussjiaoliuzhongxinEntity> wrapper);

	List<DiscussjiaoliuzhongxinView> selectListView(Pagination page,@Param("ew") Wrapper<DiscussjiaoliuzhongxinEntity> wrapper);
	
	DiscussjiaoliuzhongxinView selectView(@Param("ew") Wrapper<DiscussjiaoliuzhongxinEntity> wrapper);
	

}
