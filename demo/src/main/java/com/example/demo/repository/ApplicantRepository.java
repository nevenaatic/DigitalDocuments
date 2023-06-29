package com.example.demo.repository;

import com.example.demo.model.Applicant;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicantRepository extends ElasticsearchRepository<Applicant, Integer> {
}
