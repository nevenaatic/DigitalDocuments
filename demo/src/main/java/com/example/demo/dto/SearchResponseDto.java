package com.example.demo.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchResponseDto {
    private String id;
    private String firstName;
    private String lastName;
    private String education;
    private String highlight;
    private String address;
}
