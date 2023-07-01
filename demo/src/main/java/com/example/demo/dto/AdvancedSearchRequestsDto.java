package com.example.demo.dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AdvancedSearchRequestsDto {
    private String criteria;
    private String op;
    private String content;
    private Boolean phrase;
}
