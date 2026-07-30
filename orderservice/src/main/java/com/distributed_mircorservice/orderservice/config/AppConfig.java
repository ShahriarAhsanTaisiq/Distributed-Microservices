package com.distributed_mircorservice.orderservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

   // Using RestTemplate Approach (With Default setTimeout)
    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

}
