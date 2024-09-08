package com.cl.dao;

import com.cl.entity.ZixunfenleiEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.cl.entity.view.ZixunfenleiView;


/**
 * 资讯分类
 * 
 * @author 
 * @email 
 * @date 2024-04-25 10:27:11
 */
public interface ZixunfenleiDao extends BaseMapper<ZixunfenleiEntity> {
	
	List<ZixunfenleiView> selectListView(@Param("ew") Wrapper<ZixunfenleiEntity> wrapper);

	List<ZixunfenleiView> selectListView(Pagination page,@Param("ew") Wrapper<ZixunfenleiEntity> wrapper);
	
	ZixunfenleiView selectView(@Param("ew") Wrapper<ZixunfenleiEntity> wrapper);
	

}
