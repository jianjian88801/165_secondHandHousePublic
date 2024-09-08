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

import com.cl.entity.ErshoufangxinxiEntity;
import com.cl.entity.view.ErshoufangxinxiView;

import com.cl.service.ErshoufangxinxiService;
import com.cl.service.TokenService;
import com.cl.utils.PageUtils;
import com.cl.utils.R;
import com.cl.utils.MPUtil;
import com.cl.utils.CommonUtil;
import com.cl.service.StoreupService;
import com.cl.entity.StoreupEntity;

/**
 * 二手房信息
 * 后端接口
 * @author 
 * @email 
 * @date 2024-04-25 10:27:10
 */
@RestController
@RequestMapping("/ershoufangxinxi")
public class ErshoufangxinxiController {
    @Autowired
    private ErshoufangxinxiService ershoufangxinxiService;



    @Autowired
    private StoreupService storeupService;


    


    /**
     * 后端列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,ErshoufangxinxiEntity ershoufangxinxi, 
                @RequestParam(required = false) Double fangwujiagestart,
                @RequestParam(required = false) Double fangwujiageend,
		HttpServletRequest request){

		String tableName = request.getSession().getAttribute("tableName").toString();
		if(tableName.equals("maijia")) {
			ershoufangxinxi.setMaijiazhanghao((String)request.getSession().getAttribute("username"));
		}
        EntityWrapper<ErshoufangxinxiEntity> ew = new EntityWrapper<ErshoufangxinxiEntity>();

                if(fangwujiagestart!=null) ew.ge("fangwujiage", fangwujiagestart);
                if(fangwujiageend!=null) ew.le("fangwujiage", fangwujiageend);

		PageUtils page = ershoufangxinxiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, ershoufangxinxi), params), params));
        return R.ok().put("data", page);
    }
    
    /**
     * 前端列表
     */
	@IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,ErshoufangxinxiEntity ershoufangxinxi, 
                @RequestParam(required = false) Double fangwujiagestart,
                @RequestParam(required = false) Double fangwujiageend,
		HttpServletRequest request){
        EntityWrapper<ErshoufangxinxiEntity> ew = new EntityWrapper<ErshoufangxinxiEntity>();
                if(fangwujiagestart!=null) ew.ge("fangwujiage", fangwujiagestart);
                if(fangwujiageend!=null) ew.le("fangwujiage", fangwujiageend);

		PageUtils page = ershoufangxinxiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, ershoufangxinxi), params), params));
        return R.ok().put("data", page);
    }

	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( ErshoufangxinxiEntity ershoufangxinxi){
       	EntityWrapper<ErshoufangxinxiEntity> ew = new EntityWrapper<ErshoufangxinxiEntity>();
      	ew.allEq(MPUtil.allEQMapPre( ershoufangxinxi, "ershoufangxinxi")); 
        return R.ok().put("data", ershoufangxinxiService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(ErshoufangxinxiEntity ershoufangxinxi){
        EntityWrapper< ErshoufangxinxiEntity> ew = new EntityWrapper< ErshoufangxinxiEntity>();
 		ew.allEq(MPUtil.allEQMapPre( ershoufangxinxi, "ershoufangxinxi")); 
		ErshoufangxinxiView ershoufangxinxiView =  ershoufangxinxiService.selectView(ew);
		return R.ok("查询二手房信息成功").put("data", ershoufangxinxiView);
    }
	
    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        ErshoufangxinxiEntity ershoufangxinxi = ershoufangxinxiService.selectById(id);
		ershoufangxinxi = ershoufangxinxiService.selectView(new EntityWrapper<ErshoufangxinxiEntity>().eq("id", id));
        return R.ok().put("data", ershoufangxinxi);
    }

    /**
     * 前端详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        ErshoufangxinxiEntity ershoufangxinxi = ershoufangxinxiService.selectById(id);
		ershoufangxinxi = ershoufangxinxiService.selectView(new EntityWrapper<ErshoufangxinxiEntity>().eq("id", id));
        return R.ok().put("data", ershoufangxinxi);
    }
    



    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody ErshoufangxinxiEntity ershoufangxinxi, HttpServletRequest request){
    	ershoufangxinxi.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(ershoufangxinxi);

        ershoufangxinxiService.insert(ershoufangxinxi);
        return R.ok();
    }
    
    /**
     * 前端保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody ErshoufangxinxiEntity ershoufangxinxi, HttpServletRequest request){
    	ershoufangxinxi.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(ershoufangxinxi);

        ershoufangxinxiService.insert(ershoufangxinxi);
        return R.ok();
    }


    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    public R update(@RequestBody ErshoufangxinxiEntity ershoufangxinxi, HttpServletRequest request){
        //ValidatorUtils.validateEntity(ershoufangxinxi);
        ershoufangxinxiService.updateById(ershoufangxinxi);//全部更新
        return R.ok();
    }

    
    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        ershoufangxinxiService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
	







}
