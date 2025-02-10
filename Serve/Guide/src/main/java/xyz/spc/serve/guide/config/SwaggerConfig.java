package xyz.spc.serve.guide.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger配置
 */
@Slf4j
@Configuration
@ComponentScan(basePackages = "xyz.spc.serve.guide.controller")
public class SwaggerConfig {


    @Bean
    public OpenAPI springOpenAPI() {
        log.debug("SwaggerConfig init");
        return new OpenAPI()
                .openapi("3.0.0")
                .info(new Info().title("Guide API文档")
                        .contact(new Contact())
                        .description("管理 API文档")
                        .version("1.0.0"));

    }
}
