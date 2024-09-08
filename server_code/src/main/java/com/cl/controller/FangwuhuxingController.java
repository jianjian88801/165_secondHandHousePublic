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

import com.cl.entity.FangwuhuxingEntity;
import com.cl.entity.view.FangwuhuxingView;

import com.cl.service.FangwuhuxingService;
import com.cl.service.TokenService;
import com.cl.utils.PageUtils;
import com.cl.utils.R;
import com.cl.utils.MPUtil;
import com.cl.utils.CommonUtil;

/**
 * 房屋户型
 * 后端接口
 * @author 
 * @email 
 * @date 2024-04-25 10:27:10
 */
@RestController
@RequestMapping("/fangwuhuxing")
public class FangwuhuxingController {
    @Autowired
    private FangwuhuxingService fangwuhuxingService;





    


    /**
     * 后端列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,FangwuhuxingEntity fangwuhuxing, 
		HttpServletRequest request){

        EntityWrapper<FangwuhuxingEntity> ew = new EntityWrapper<FangwuhuxingEntity>();


		PageUtils page = fangwuhuxingService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, fangwuhuxing), params), params));
        return R.ok().put("data", page);
    }
    
    /**
     * 前端列表
     */
	@IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,FangwuhuxingEntity fangwuhuxing, 
		HttpServletRequest request){
        EntityWrapper<FangwuhuxingEntity> ew = new EntityWrapper<FangwuhuxingEntity>();

		PageUtils page = fangwuhuxingService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, fangwuhuxing), params), params));
        return R.ok().put("data", page);
    }

	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( FangwuhuxingEntity fangwuhuxing){
       	EntityWrapper<FangwuhuxingEntity> ew = new EntityWrapper<FangwuhuxingEntity>();
      	ew.allEq(MPUtil.allEQMapPre( fangwuhuxing, "fangwuhuxing")); 
        return R.ok().put("data", fangwuhuxingService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(FangwuhuxingEntity fangwuhuxing){
        EntityWrapper< FangwuhuxingEntity> ew = new EntityWrapper< FangwuhuxingEntity>();
 		ew.allEq(MPUtil.allEQMapPre( fangwuhuxing, "fangwuhuxing")); 
		FangwuhuxingView fangwuhuxingView =  fangwuhuxingService.selectView(ew);
		return R.ok("查询房屋户型成功").put("data", fangwuhuxingView);
    }
	
    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        FangwuhuxingEntity fangwuhuxing = fangwuhuxingService.selectById(id);
		fangwuhuxing = fangwuhuxingService.selectView(new EntityWrapper<FangwuhuxingEntity>().eq("id", id));
        return R.ok().put("data", fangwuhuxing);
    }

    /**
     * 前端详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        FangwuhuxingEntity fangwuhuxing = fangwuhuxingService.selectById(id);
		fangwuhuxing = fangwuhuxingService.selectView(new EntityWrapper<FangwuhuxingEntity>().eq("id", id));
        return R.ok().put("data", fangwuhuxing);
    }
    



    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody FangwuhuxingEntity fangwuhuxing, HttpServletRequest request){
    	fangwuhuxing.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(fangwuhuxing);

        fangwuhuxingService.insert(fangwuhuxing);
        return R.ok();
    }
    
    /**
     * 前端保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody FangwuhuxingEntity fangwuhuxing, HttpServletRequest request){
    	fangwuhuxing.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(fangwuhuxing);

        fangwuhuxingService.insert(fangwuhuxing);
        return R.ok();
    }


    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    public R update(@RequestBody FangwuhuxingEntity fangwuhuxing, HttpServletRequest request){
        //ValidatorUtils.validateEntity(fangwuhuxing);
        fangwuhuxingService.updateById(fangwuhuxing);//全部更新
        return R.ok();
    }

    
    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        fangwuhuxingService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
	







}
