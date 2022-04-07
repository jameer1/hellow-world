package com.rjtech.config;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Configuration
@ImportResource("classpath*:META-INF/spring/services-context.xml")
@EnableTransactionManagement(order = 20)
@AutoConfigureAfter(value = { GenericXMLConfig.class, EmailConfig.class })
public class AppConfig {

	@Bean(name = "multipartResolver")
    public CommonsMultipartResolver multipartResolver() {
        return new CommonsMultipartResolver();
    }
}