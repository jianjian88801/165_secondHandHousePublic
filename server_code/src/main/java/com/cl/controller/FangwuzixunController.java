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

import com.cl.entity.FangwuzixunEntity;
import com.cl.entity.view.FangwuzixunView;

import com.cl.service.FangwuzixunService;
import com.cl.service.TokenService;
import com.cl.utils.PageUtils;
import com.cl.utils.R;
import com.cl.utils.MPUtil;
import com.cl.utils.CommonUtil;

/**
 * 房屋资讯
 * 后端接口
 * @author 
 * @email 
 * @date 2024-04-25 10:27:11
 */
@RestController
@RequestMapping("/fangwuzixun")
public class FangwuzixunController {
    @Autowired
    private FangwuzixunService fangwuzixunService;





    


    /**
     * 后端列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,FangwuzixunEntity fangwuzixun, 
                @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date fabushijianstart,
                @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date fabushijianend,
		HttpServletRequest request){

        EntityWrapper<FangwuzixunEntity> ew = new EntityWrapper<FangwuzixunEntity>();

                if(fabushijianstart!=null) ew.ge("fabushijian", fabushijianstart);
                if(fabushijianend!=null) ew.le("fabushijian", fabushijianend);

		PageUtils page = fangwuzixunService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, fangwuzixun), params), params));
        return R.ok().put("data", page);
    }
    
    /**
     * 前端列表
     */
	@IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,FangwuzixunEntity fangwuzixun, 
                @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date fabushijianstart,
                @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date fabushijianend,
		HttpServletRequest request){
        EntityWrapper<FangwuzixunEntity> ew = new EntityWrapper<FangwuzixunEntity>();
                if(fabushijianstart!=null) ew.ge("fabushijian", fabushijianstart);
                if(fabushijianend!=null) ew.le("fabushijian", fabushijianend);

		PageUtils page = fangwuzixunService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, fangwuzixun), params), params));
        return R.ok().put("data", page);
    }

	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( FangwuzixunEntity fangwuzixun){
       	EntityWrapper<FangwuzixunEntity> ew = new EntityWrapper<FangwuzixunEntity>();
      	ew.allEq(MPUtil.allEQMapPre( fangwuzixun, "fangwuzixun")); 
        return R.ok().put("data", fangwuzixunService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(FangwuzixunEntity fangwuzixun){
        EntityWrapper< FangwuzixunEntity> ew = new EntityWrapper< FangwuzixunEntity>();
 		ew.allEq(MPUtil.allEQMapPre( fangwuzixun, "fangwuzixun")); 
		FangwuzixunView fangwuzixunView =  fangwuzixunService.selectView(ew);
		return R.ok("查询房屋资讯成功").put("data", fangwuzixunView);
    }
	
    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        FangwuzixunEntity fangwuzixun = fangwuzixunService.selectById(id);
		fangwuzixun = fangwuzixunService.selectView(new EntityWrapper<FangwuzixunEntity>().eq("id", id));
        return R.ok().put("data", fangwuzixun);
    }

    /**
     * 前端详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        FangwuzixunEntity fangwuzixun = fangwuzixunService.selectById(id);
		fangwuzixun = fangwuzixunService.selectView(new EntityWrapper<FangwuzixunEntity>().eq("id", id));
        return R.ok().put("data", fangwuzixun);
    }
    



    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody FangwuzixunEntity fangwuzixun, HttpServletRequest request){
    	fangwuzixun.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(fangwuzixun);

        fangwuzixunService.insert(fangwuzixun);
        return R.ok();
    }
    
    /**
     * 前端保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody FangwuzixunEntity fangwuzixun, HttpServletRequest request){
    	fangwuzixun.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(fangwuzixun);

        fangwuzixunService.insert(fangwuzixun);
        return R.ok();
    }


    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    public R update(@RequestBody FangwuzixunEntity fangwuzixun, HttpServletRequest request){
        //ValidatorUtils.validateEntity(fangwuzixun);
        fangwuzixunService.updateById(fangwuzixun);//全部更新
        return R.ok();
    }

    
    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        fangwuzixunService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
	







}
