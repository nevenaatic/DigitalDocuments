package com.example.demo.service;

import com.example.demo.model.Applicant;
import com.example.demo.repository.ApplicantRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ApplicantService {
    private final ApplicantRepository applicantRepository;

    public void save(Applicant applicant){
        applicantRepository.save(applicant);
    }

    public Applicant findById(int id){
        return (Applicant) applicantRepository.findById(id).orElse(null);
    }

}
