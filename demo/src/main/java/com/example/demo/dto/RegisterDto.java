package com.example.demo.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {
    private String name;
    private String surname;
    private String street;
    private String city;
    private String education;
    private MultipartFile cv;
    private MultipartFile coverLetter;
}
