package com.cl.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import com.cl.utils.ValidatorUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.cl.annotation.IgnoreAuth;

import com.cl.entity.DiscussfangwuzixunEntity;
import com.cl.entity.view.DiscussfangwuzixunView;

import com.cl.service.DiscussfangwuzixunService;
import com.cl.service.TokenService;
import com.cl.utils.PageUtils;
import com.cl.utils.R;
import com.cl.utils.MPUtil;
import com.cl.utils.CommonUtil;

/**
 * 房屋资讯评论表
 * 后端接口
 * @author 
 * @email 
 * @date 2024-04-25 10:27:11
 */
@RestController
@RequestMapping("/discussfangwuzixun")
public class DiscussfangwuzixunController {
    @Autowired
    private DiscussfangwuzixunService discussfangwuzixunService;





    


    /**
     * 后端列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,DiscussfangwuzixunEntity discussfangwuzixun, 
		HttpServletRequest request){

        EntityWrapper<DiscussfangwuzixunEntity> ew = new EntityWrapper<DiscussfangwuzixunEntity>();


		PageUtils page = discussfangwuzixunService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, discussfangwuzixun), params), params));
        return R.ok().put("data", page);
    }
    
    /**
     * 前端列表
     */
	@IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,DiscussfangwuzixunEntity discussfangwuzixun, 
		HttpServletRequest request){
        EntityWrapper<DiscussfangwuzixunEntity> ew = new EntityWrapper<DiscussfangwuzixunEntity>();

		PageUtils page = discussfangwuzixunService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, discussfangwuzixun), params), params));
        return R.ok().put("data", page);
    }

	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( DiscussfangwuzixunEntity discussfangwuzixun){
       	EntityWrapper<DiscussfangwuzixunEntity> ew = new EntityWrapper<DiscussfangwuzixunEntity>();
      	ew.allEq(MPUtil.allEQMapPre( discussfangwuzixun, "discussfangwuzixun")); 
        return R.ok().put("data", discussfangwuzixunService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(DiscussfangwuzixunEntity discussfangwuzixun){
        EntityWrapper< DiscussfangwuzixunEntity> ew = new EntityWrapper< DiscussfangwuzixunEntity>();
 		ew.allEq(MPUtil.allEQMapPre( discussfangwuzixun, "discussfangwuzixun")); 
		DiscussfangwuzixunView discussfangwuzixunView =  discussfangwuzixunService.selectView(ew);
		return R.ok("查询房屋资讯评论表成功").put("data", discussfangwuzixunView);
    }
	
    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        DiscussfangwuzixunEntity discussfangwuzixun = discussfangwuzixunService.selectById(id);
		discussfangwuzixun = discussfangwuzixunService.selectView(new EntityWrapper<DiscussfangwuzixunEntity>().eq("id", id));
        return R.ok().put("data", discussfangwuzixun);
    }

    /**
     * 前端详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        DiscussfangwuzixunEntity discussfangwuzixun = discussfangwuzixunService.selectById(id);
		discussfangwuzixun = discussfangwuzixunService.selectView(new EntityWrapper<DiscussfangwuzixunEntity>().eq("id", id));
        return R.ok().put("data", discussfangwuzixun);
    }
    



    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody DiscussfangwuzixunEntity discussfangwuzixun, HttpServletRequest request){
    	discussfangwuzixun.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(discussfangwuzixun);

        discussfangwuzixunService.insert(discussfangwuzixun);
        return R.ok();
    }
    
    /**
     * 前端保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody DiscussfangwuzixunEntity discussfangwuzixun, HttpServletRequest request){
    	discussfangwuzixun.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(discussfangwuzixun);

        discussfangwuzixunService.insert(discussfangwuzixun);
        return R.ok();
    }


    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    @IgnoreAuth
    public R update(@RequestBody DiscussfangwuzixunEntity discussfangwuzixun, HttpServletRequest request){
        //ValidatorUtils.validateEntity(discussfangwuzixun);
        discussfangwuzixunService.updateById(discussfangwuzixun);//全部更新
        return R.ok();
    }

    
    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        discussfangwuzixunService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
	
	/**
     * 前端智能排序
     */
	@IgnoreAuth
    @RequestMapping("/autoSort")
    public R autoSort(@RequestParam Map<String, Object> params,DiscussfangwuzixunEntity discussfangwuzixun, HttpServletRequest request,String pre){
        EntityWrapper<DiscussfangwuzixunEntity> ew = new EntityWrapper<DiscussfangwuzixunEntity>();
        Map<String, Object> newMap = new HashMap<String, Object>();
        Map<String, Object> param = new HashMap<String, Object>();
		Iterator<Map.Entry<String, Object>> it = param.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Object> entry = it.next();
			String key = entry.getKey();
			String newKey = entry.getKey();
			if (pre.endsWith(".")) {
				newMap.put(pre + newKey, entry.getValue());
			} else if (StringUtils.isEmpty(pre)) {
				newMap.put(newKey, entry.getValue());
			} else {
				newMap.put(pre + "." + newKey, entry.getValue());
			}
		}
		params.put("sort", "clicktime");
        params.put("order", "desc");
		PageUtils page = discussfangwuzixunService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, discussfangwuzixun), params), params));
        return R.ok().put("data", page);
    }







}
