package com.rjtech.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestClientConfig {
    @Bean
    public RestTemplate restTemplate() {

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(getJacksonMessageConvert());

        return restTemplate;

    }

    public MappingJackson2HttpMessageConverter getJacksonMessageConvert() {
        return new MappingJackson2HttpMessageConverter();
    }

}
