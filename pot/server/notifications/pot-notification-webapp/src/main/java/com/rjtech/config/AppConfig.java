package com.rjtech.config;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ImportResource("classpath*:META-INF/spring/services-context.xml")
@AutoConfigureAfter(value = { GenericXMLConfig.class })
@EnableTransactionManagement(order = 20)
public class AppConfig {

}