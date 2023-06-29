package com.example.demo.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.elasticsearch.client.RestHighLevelClient;

@Configuration
public class Config {

    @Bean
    public RestTemplate configureRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    public RestHighLevelClient client() {
        return new RestHighLevelClient(RestClient.builder(
                new HttpHost("localhost", 9200, "http")));
    }
}
