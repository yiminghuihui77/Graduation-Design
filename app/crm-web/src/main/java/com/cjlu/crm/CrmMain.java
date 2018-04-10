package com.cjlu.crm;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 应用程序启动入口
 *
 * @author minghui.y
 * @create 2018-03-17 12:06
 **/
@SpringBootApplication
@MapperScan("com.cjlu.crm.dao")
//@ServletComponentScan
public class CrmMain {
    private static final Logger LOGGER = LoggerFactory.getLogger(CrmMain.class);

    public static void main(String[] args) {
        //启动SpringBoot项目
        SpringApplication app = new SpringApplication(CrmMain.class);
        app.run(args);
        LOGGER.info("Crm系统启动 -- " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

    }
}
