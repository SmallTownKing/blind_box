package com.damochaohe.app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * App 端启动类。
 *
 * <p>用于承载 APP 与小程序端 API，默认暴露 `/api/app/**` 路径接口。</p>
 */
@MapperScan("com.damochaohe.**.mapper")
@SpringBootApplication(scanBasePackages = "com.damochaohe")
public class BlindBoxAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlindBoxAppApplication.class, args);
    }
}
