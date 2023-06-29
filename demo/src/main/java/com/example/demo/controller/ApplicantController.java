package com.example.demo.controller;

import com.example.demo.model.Applicant;
import com.example.demo.service.ApplicantService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("applicant")
@AllArgsConstructor
public class ApplicantController {

    private final ApplicantService applicantService;

    @PostMapping
    public void save(@RequestBody Applicant applicant){
        applicantService.save(applicant);
    }

    @GetMapping("/{id}")
    public Applicant findById(@PathVariable int id){
        return applicantService.findById(id);
    }
}
