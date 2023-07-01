package com.example.demo.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SimpleSearchDto {

    private String content;
    private Boolean phrase;
}
