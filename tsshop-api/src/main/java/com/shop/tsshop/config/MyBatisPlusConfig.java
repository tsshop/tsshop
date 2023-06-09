package com.shop.tsshop.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : tsshop
 * @date : 2023/6/5
 */
@Configuration
public class MyBatisPlusConfig {

    @Bean
    @ConditionalOnClass(value = PaginationInterceptor.class)
    public PaginationInterceptor paginationInterceptor(){
        return new PaginationInterceptor();
    }
}
