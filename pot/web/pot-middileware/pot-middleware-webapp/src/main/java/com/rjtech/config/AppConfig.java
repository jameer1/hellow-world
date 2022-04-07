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
@AutoConfigureAfter(value = { RestClientConfig.class, GenericXMLConfig.class })
public class AppConfig {

	static {
		
		System.out.println(" Just Texsting ");
	}
	
	
    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver multipartResolver() {
    	System.out.println(" Dinesh ");
    	
    
        return new CommonsMultipartResolver();
    }
}