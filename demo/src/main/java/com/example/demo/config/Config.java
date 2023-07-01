package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.web.client.RestTemplate;
import org.elasticsearch.client.RestHighLevelClient;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.example.demo.repository")
@ComponentScan(basePackages = {"com.example.demo.service"})
public class Config extends AbstractElasticsearchConfiguration {

    @Bean
    public RestTemplate configureRestTemplate() {
        return new RestTemplate();
    }

    @Value("${elasticsearch.url}")
    public String elasticSearchUrl;

    @Override
    public RestHighLevelClient elasticsearchClient() {
        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo(elasticSearchUrl)
                .build();

        return RestClients.create(clientConfiguration)
                .rest();
    }
}
