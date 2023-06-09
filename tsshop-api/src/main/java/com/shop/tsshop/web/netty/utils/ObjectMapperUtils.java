package com.shop.tsshop.web.netty.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;

/**
 * @Desc :
 * @Create : tsshop ~ 2020/06/14
 */
public class ObjectMapperUtils {

    private ObjectMapperUtils() {
    }

    private final static ObjectMapper objectMapper = new ObjectMapper();

    public static ObjectMapper getInstance(){
        //反序列化的时候如果多了其他属性,不抛出异常
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        //如果是空对象的时候,不抛异常
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        return objectMapper;
    }

    public static <T> T readValue(String json,Class<T> clazz){
        try {
            return getInstance().readValue(json,clazz);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String toJsonString(Object obj){
        try {
            return getInstance().writeValueAsString(obj);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
