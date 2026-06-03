package com.damochaohe.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 后台管理端启动类。
 *
 * <p>用于承载运营后台、配置后台、报表后台接口。</p>
 */
@MapperScan("com.damochaohe.**.mapper")
@SpringBootApplication(scanBasePackages = "com.damochaohe")
public class BlindBoxAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlindBoxAdminApplication.class, args);
    }
}
