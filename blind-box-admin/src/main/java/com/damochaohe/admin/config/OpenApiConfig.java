package com.damochaohe.admin.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 后台管理端 OpenAPI 文档配置。
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI adminOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("大魔潮盒后台接口文档")
                        .description("用于运营后台与管理后台联调的接口文档。")
                        .version("1.0.0")
                        .license(new License().name("Internal Use Only")));
    }
}
