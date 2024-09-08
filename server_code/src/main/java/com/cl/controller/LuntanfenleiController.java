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

import com.cl.entity.LuntanfenleiEntity;
import com.cl.entity.view.LuntanfenleiView;

import com.cl.service.LuntanfenleiService;
import com.cl.service.TokenService;
import com.cl.utils.PageUtils;
import com.cl.utils.R;
import com.cl.utils.MPUtil;
import com.cl.utils.CommonUtil;

/**
 * 论坛分类
 * 后端接口
 * @author 
 * @email 
 * @date 2024-04-25 10:27:10
 */
@RestController
@RequestMapping("/luntanfenlei")
public class LuntanfenleiController {
    @Autowired
    private LuntanfenleiService luntanfenleiService;





    


    /**
     * 后端列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,LuntanfenleiEntity luntanfenlei, 
		HttpServletRequest request){

        EntityWrapper<LuntanfenleiEntity> ew = new EntityWrapper<LuntanfenleiEntity>();


		PageUtils page = luntanfenleiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, luntanfenlei), params), params));
        return R.ok().put("data", page);
    }
    
    /**
     * 前端列表
     */
	@IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,LuntanfenleiEntity luntanfenlei, 
		HttpServletRequest request){
        EntityWrapper<LuntanfenleiEntity> ew = new EntityWrapper<LuntanfenleiEntity>();

		PageUtils page = luntanfenleiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, luntanfenlei), params), params));
        return R.ok().put("data", page);
    }

	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( LuntanfenleiEntity luntanfenlei){
       	EntityWrapper<LuntanfenleiEntity> ew = new EntityWrapper<LuntanfenleiEntity>();
      	ew.allEq(MPUtil.allEQMapPre( luntanfenlei, "luntanfenlei")); 
        return R.ok().put("data", luntanfenleiService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(LuntanfenleiEntity luntanfenlei){
        EntityWrapper< LuntanfenleiEntity> ew = new EntityWrapper< LuntanfenleiEntity>();
 		ew.allEq(MPUtil.allEQMapPre( luntanfenlei, "luntanfenlei")); 
		LuntanfenleiView luntanfenleiView =  luntanfenleiService.selectView(ew);
		return R.ok("查询论坛分类成功").put("data", luntanfenleiView);
    }
	
    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        LuntanfenleiEntity luntanfenlei = luntanfenleiService.selectById(id);
		luntanfenlei = luntanfenleiService.selectView(new EntityWrapper<LuntanfenleiEntity>().eq("id", id));
        return R.ok().put("data", luntanfenlei);
    }

    /**
     * 前端详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        LuntanfenleiEntity luntanfenlei = luntanfenleiService.selectById(id);
		luntanfenlei = luntanfenleiService.selectView(new EntityWrapper<LuntanfenleiEntity>().eq("id", id));
        return R.ok().put("data", luntanfenlei);
    }
    



    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody LuntanfenleiEntity luntanfenlei, HttpServletRequest request){
    	luntanfenlei.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(luntanfenlei);

        luntanfenleiService.insert(luntanfenlei);
        return R.ok();
    }
    
    /**
     * 前端保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody LuntanfenleiEntity luntanfenlei, HttpServletRequest request){
    	luntanfenlei.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(luntanfenlei);

        luntanfenleiService.insert(luntanfenlei);
        return R.ok();
    }


    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    public R update(@RequestBody LuntanfenleiEntity luntanfenlei, HttpServletRequest request){
        //ValidatorUtils.validateEntity(luntanfenlei);
        luntanfenleiService.updateById(luntanfenlei);//全部更新
        return R.ok();
    }

    
    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        luntanfenleiService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
	







}
