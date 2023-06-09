package com.shop.tsshop.web.controller;


import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.tsshop.config.ApiResult;
import com.shop.tsshop.config.token.PassToken;
import com.shop.tsshop.web.model.domain.Goods;
import com.shop.tsshop.web.model.domain.Seckill;
import com.shop.tsshop.web.model.dto.SeckillDto;
import com.shop.tsshop.web.model.vo.SeckillGoodsVo;
import com.shop.tsshop.web.service.SeckillGoodsService;
import com.shop.tsshop.web.service.SeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 秒杀活动 前端控制器
 * </p>
 *
 * @author xu
 * @since 2023-02-21
 */
@RestController
@RequestMapping("/seckill")
public class SeckillController {

    @Autowired
    SeckillService seckillService;

    @Autowired
    SeckillGoodsService seckillGoodsService;

    //首页秒杀商品 时间
    @GetMapping("/getHome")
    @PassToken
    public ApiResult<Object> getHomeList() {
        JSONObject json=new JSONObject();
        Seckill seckill=seckillService.getOne(new LambdaQueryWrapper<Seckill>().eq(Seckill::getStatus,1));
        if(seckill==null){
            return ApiResult.ok(null);
        }
//没讲展示规则， 默认商品或sku都展示，也就是
        int[] times= StrUtil.splitToInt(seckill.getTimeInterval(),",");
        if(times.length==0){
            return ApiResult.ok(null);
        }
        PageHelper.startPage(1, 4 ,"time" );
        List<SeckillGoodsVo> list=seckillGoodsService.getGoodsVo(seckill.getId(),-1);

        //获取活动开始时间
        Date date= new Date(Math.max(new Date().getTime(),seckill.getStartTime().getTime()));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.SECOND,0); //这是将当天的【秒】设置为0
        calendar.set(Calendar.MINUTE,0); //这是将当天的【分】设置为0

        int k=0;
        loop:while (true){
            calendar.add(Calendar.DAY_OF_MONTH, k);;
            for(int t:times){
                calendar.set(Calendar.HOUR_OF_DAY,t);//这是将当天的【时】设置为下一场开始时间
                //如果下一场未开始，作为倒计时返回
                if(calendar.getTime().getTime()>System.currentTimeMillis()){
                    break loop;
                }
            }
            k++;
        }

        date=calendar.getTime();
        json.put("time",date);
        json.put("goods",list);
        return ApiResult.ok(json);
    }
    //时间列表
    @GetMapping("/getTimeList")
    public ApiResult<Object> getTimeList() {
        Seckill seckill=seckillService.getOne(new LambdaQueryWrapper<Seckill>().eq(Seckill::getStatus,1));
//没讲展示规则， 默认商品或sku都展示，也就是
        int[] times= StrUtil.splitToInt(seckill.getTimeInterval(),",");
        return ApiResult.ok(times);
    }

    //商品列表
    @PostMapping("/getGoods")
    public ApiResult<Object> getGoods(@RequestBody SeckillDto dto) {
        Seckill seckill=seckillService.getOne(new LambdaQueryWrapper<Seckill>().eq(Seckill::getStatus,1));
        PageHelper.startPage(dto.getPageNumber(), dto.getPageSize());
        List<SeckillGoodsVo> list=seckillGoodsService.getGoodsVo(seckill.getId(),dto.getTime());
        PageInfo<SeckillGoodsVo> p = new PageInfo<>(list);
        return ApiResult.ok(p);
    }
}
