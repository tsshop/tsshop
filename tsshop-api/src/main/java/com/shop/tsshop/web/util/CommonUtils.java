package com.shop.tsshop.web.util;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * @author wyh
 * @date 2020/12/14
 */
public class CommonUtils {



    /**
     * 生成uuid
     */
    public static String generatorUUID(){
        String uuid = UUID.randomUUID().toString().replace("-","").substring(0,12).toUpperCase();
        return uuid;
    }


    /**
     * 价格转换
     * @param bigDecimal
     * @return price
     */
    public static String bigToString(BigDecimal bigDecimal){
        String price = bigDecimal.multiply(new BigDecimal(100)).stripTrailingZeros().toPlainString();
        return price;
    }

    /**
     * 获得当前时间
     * @return date
     */
    public static Date getDtaeTime() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formatter.format(date);
        return date;
    }

    /**
     * 获取订单到期时间
     * @return date
     */
    public static Timestamp getRemainTime(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.MINUTE, 30);
        Timestamp date =  Timestamp.valueOf(formatter.format(nowTime.getTime()));

        return date;
    }

}
