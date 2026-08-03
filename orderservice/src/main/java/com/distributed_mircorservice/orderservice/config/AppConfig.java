package com.distributed_mircorservice.orderservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

   // Using RestTemplate Approach (With Default setTimeout)
    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
//
//    // Another way using RestTemplate if we want to set timeouts manually
//
//    @Bean
//    public RestTemplate getRestTemplate2() {
//        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
//
//        // Set timeout in milliseconds
//        factory.setConnectTimeout(1000); // 1 second for connection timeout
//        factory.setReadTimeout(5000); // 5 seconds for response timeout
//
//        return new RestTemplate(factory);
//    }

    @Bean
    public RestClient getRestClient(){
        // Introduced in Spring framework 6.0+ and Spring Boot 3.0+
        // Synchronous / Blocking in nature means client wait for response from the server side.
        // Modern. (Fluent Based API) that is more readable and easy to maintain
        return RestClient.create(); // Internally it is calling RestClient.builder().build(), this create a new object of restclient.

    }

}
