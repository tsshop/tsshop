package com.ts;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动程序
 *
 * @author ruoyi
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@Slf4j
public class Application
{
    public static void main(String[] args)
    {
        SpringApplication.run(Application.class, args);
        log.info("TS SHOP 启动成功");
        System.out.println("(♥◠‿◠)ﾉﾞ  商城后台启动成功   ლ(´ڡ`ლ)ﾞ  \n" +
                "\n" +
                "████████╗███████╗    ███████╗██╗  ██╗ ██████╗ ██████╗ \n" +
                "╚══██╔══╝██╔════╝    ██╔════╝██║  ██║██╔═══██╗██╔══██╗\n" +
                "   ██║   ███████╗    ███████╗███████║██║   ██║██████╔╝\n" +
                "   ██║   ╚════██║    ╚════██║██╔══██║██║   ██║██╔═══╝ \n" +
                "   ██║   ███████║    ███████║██║  ██║╚██████╔╝██║     \n" +
                "   ╚═╝   ╚══════╝    ╚══════╝╚═╝  ╚═╝ ╚═════╝ ╚═╝     \n" +
                "                                                      \n");
    }
}
