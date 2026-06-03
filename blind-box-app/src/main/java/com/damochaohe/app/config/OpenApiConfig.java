package com.damochaohe.app.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * APP 端 OpenAPI 文档配置。
 *
 * <p>Knife4j 基于 OpenAPI 3 渲染，因此这里只需提供标准文档元信息。</p>
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI appOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("大魔潮盒 APP/小程序接口文档")
                        .description("用于 APP 与小程序端联调的接口文档，后续新增接口应同步补充注释。")
                        .version("1.0.0")
                        .license(new License().name("Internal Use Only")));
    }
}
