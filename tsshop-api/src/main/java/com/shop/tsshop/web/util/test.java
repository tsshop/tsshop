package com.shop.tsshop.web.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class test {
    public static void main(String[] args) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date sd=new Date(1676995200);
        Date date= new Date(Math.max(new Date().getTime(),sd.getTime()));
        System.out.println("较大的时间："+sdf.format(date));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.SECOND,0); //这是将当天的【秒】设置为0
        calendar.set(Calendar.MINUTE,0); //这是将当天的【分】设置为0
        calendar.set(Calendar.HOUR_OF_DAY,19); //这是将当天的【时】设置为0
        date=calendar.getTime();
        System.out.println("开始时间："+sdf.format(date));
        if((new Date()).getTime()>date.getTime()){
            System.out.println("-------------------");
        }
    }
}
