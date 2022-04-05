package com.zjc.base;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author zjc
 * @create 2022-01-05
 */
@SpringBootApplication
@MapperScan({"com.zjc.base.module.*.dao", "com.zhilink.pf.cp.message.dao"})
@ComponentScan({
        "com.zjc.base",
        "com.zhilink.pf.framework.config",
        "com.zhilink.pf.framework.utils",
        "com.zhilink.pf.framework.advisor"})
@EnableAsync
public class StartApplication {
    public static void main(String[] args) {
        SpringApplication.run(StartApplication.class, args);
    }
}
