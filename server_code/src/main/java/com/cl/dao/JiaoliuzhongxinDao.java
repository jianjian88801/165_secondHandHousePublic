package com.cl.dao;

import com.cl.entity.JiaoliuzhongxinEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.cl.entity.view.JiaoliuzhongxinView;


/**
 * 交流中心
 * 
 * @author 
 * @email 
 * @date 2024-04-25 10:27:10
 */
public interface JiaoliuzhongxinDao extends BaseMapper<JiaoliuzhongxinEntity> {
	
	List<JiaoliuzhongxinView> selectListView(@Param("ew") Wrapper<JiaoliuzhongxinEntity> wrapper);

	List<JiaoliuzhongxinView> selectListView(Pagination page,@Param("ew") Wrapper<JiaoliuzhongxinEntity> wrapper);
	
	JiaoliuzhongxinView selectView(@Param("ew") Wrapper<JiaoliuzhongxinEntity> wrapper);
	

}
