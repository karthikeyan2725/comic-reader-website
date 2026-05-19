package com.karthikeyan2527.comic_reader_backend;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfiguration {

    @Bean
    RestClient getRestClient(){
        return RestClient.create();
    }
}
