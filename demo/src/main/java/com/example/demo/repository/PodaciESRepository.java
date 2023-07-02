package com.example.demo.repository;

import com.example.demo.model.PodaciES;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface PodaciESRepository  extends ElasticsearchRepository<PodaciES, Integer> {
}
