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

import com.cl.entity.ZixunfenleiEntity;
import com.cl.entity.view.ZixunfenleiView;

import com.cl.service.ZixunfenleiService;
import com.cl.service.TokenService;
import com.cl.utils.PageUtils;
import com.cl.utils.R;
import com.cl.utils.MPUtil;
import com.cl.utils.CommonUtil;

/**
 * 资讯分类
 * 后端接口
 * @author 
 * @email 
 * @date 2024-04-25 10:27:11
 */
@RestController
@RequestMapping("/zixunfenlei")
public class ZixunfenleiController {
    @Autowired
    private ZixunfenleiService zixunfenleiService;





    


    /**
     * 后端列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,ZixunfenleiEntity zixunfenlei, 
		HttpServletRequest request){

        EntityWrapper<ZixunfenleiEntity> ew = new EntityWrapper<ZixunfenleiEntity>();


		PageUtils page = zixunfenleiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, zixunfenlei), params), params));
        return R.ok().put("data", page);
    }
    
    /**
     * 前端列表
     */
	@IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,ZixunfenleiEntity zixunfenlei, 
		HttpServletRequest request){
        EntityWrapper<ZixunfenleiEntity> ew = new EntityWrapper<ZixunfenleiEntity>();

		PageUtils page = zixunfenleiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, zixunfenlei), params), params));
        return R.ok().put("data", page);
    }

	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( ZixunfenleiEntity zixunfenlei){
       	EntityWrapper<ZixunfenleiEntity> ew = new EntityWrapper<ZixunfenleiEntity>();
      	ew.allEq(MPUtil.allEQMapPre( zixunfenlei, "zixunfenlei")); 
        return R.ok().put("data", zixunfenleiService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(ZixunfenleiEntity zixunfenlei){
        EntityWrapper< ZixunfenleiEntity> ew = new EntityWrapper< ZixunfenleiEntity>();
 		ew.allEq(MPUtil.allEQMapPre( zixunfenlei, "zixunfenlei")); 
		ZixunfenleiView zixunfenleiView =  zixunfenleiService.selectView(ew);
		return R.ok("查询资讯分类成功").put("data", zixunfenleiView);
    }
	
    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        ZixunfenleiEntity zixunfenlei = zixunfenleiService.selectById(id);
		zixunfenlei = zixunfenleiService.selectView(new EntityWrapper<ZixunfenleiEntity>().eq("id", id));
        return R.ok().put("data", zixunfenlei);
    }

    /**
     * 前端详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        ZixunfenleiEntity zixunfenlei = zixunfenleiService.selectById(id);
		zixunfenlei = zixunfenleiService.selectView(new EntityWrapper<ZixunfenleiEntity>().eq("id", id));
        return R.ok().put("data", zixunfenlei);
    }
    



    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody ZixunfenleiEntity zixunfenlei, HttpServletRequest request){
    	zixunfenlei.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(zixunfenlei);

        zixunfenleiService.insert(zixunfenlei);
        return R.ok();
    }
    
    /**
     * 前端保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody ZixunfenleiEntity zixunfenlei, HttpServletRequest request){
    	zixunfenlei.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(zixunfenlei);

        zixunfenleiService.insert(zixunfenlei);
        return R.ok();
    }


    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    public R update(@RequestBody ZixunfenleiEntity zixunfenlei, HttpServletRequest request){
        //ValidatorUtils.validateEntity(zixunfenlei);
        zixunfenleiService.updateById(zixunfenlei);//全部更新
        return R.ok();
    }

    
    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        zixunfenleiService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
	







}
