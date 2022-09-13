package com.javatiaocao.myblog;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@SpringBootApplication
//@MapperScan(value = "com.javatiaocao.myblog.mapper")
@EnableTransactionManagement
public class MybolgApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybolgApplication.class, args);
        log.info("项目启动成功...");
    }

}
