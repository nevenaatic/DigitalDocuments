package com.example.demo.dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ApplicantDto {
    private String name;
    private String address;
    private String education;
    private String id;
    private Boolean isHired;
}
