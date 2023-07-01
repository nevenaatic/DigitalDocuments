package com.example.demo.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AdvancedSearchDto {
    private List<AdvancedSearchRequestsDto> requests;
}
