package com.example.demo.repository;

import com.example.demo.model.Applicant;
import com.example.demo.model.IndexUnit;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndexUnitRepository extends ElasticsearchRepository<IndexUnit, String> {


}
