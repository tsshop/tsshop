package com.shop.tsshop;

import com.shop.tsshop.web.netty.NettyServer;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class, SecurityFilterAutoConfiguration.class, ManagementWebSecurityAutoConfiguration.class})
//@EnableTransactionManagement
@MapperScan({"com.shop.tsshop.**.mapper"})
@Slf4j
public class TsShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(TsShopApplication.class, args);
        new NettyServer().startServer(9075);
        log.info("TS SHOP API 启动成功");
    }

}
