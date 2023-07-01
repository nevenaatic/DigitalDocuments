package com.example.demo.service;

import com.example.demo.dto.SearchResponseDto;
import com.example.demo.model.Applicant;
import com.example.demo.model.IndexUnit;
import com.example.demo.repository.ApplicantRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class SearchService {

    private final ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Autowired
    private ApplicantRepository applicationRepository;

    public List<SearchResponseDto> simpleSearch(NativeSearchQuery searchQuery){
        System.out.println(searchQuery.getQuery());
        SearchHits<IndexUnit> searchHits = elasticsearchRestTemplate.search(searchQuery, IndexUnit.class, IndexCoordinates.of("applicant"));
        return getSearchResponse(searchHits);
    }


    private List<SearchResponseDto> getSearchResponse(SearchHits<IndexUnit> searchHits) {
        List<SearchResponseDto> searchResponses = new ArrayList<>();

        for(SearchHit<IndexUnit> searchHit : searchHits) {
            SearchResponseDto searchResponse = new SearchResponseDto();

            searchResponse.setId(searchHit.getId());
            searchResponse.setFirstName(searchHit.getContent().getName());
            searchResponse.setLastName(searchHit.getContent().getSurname());
            searchResponse.setEducation(searchHit.getContent().getEducation());
            Applicant a = applicationRepository.findById(Integer.valueOf(searchHit.getId())).orElseGet(null);
            searchResponse.setAddress(a.getStreet() + ", " + a.getCity());

            if (searchHit.getHighlightFields().isEmpty()) {
                searchResponse.setHighlight(searchHit.getContent().getCvContent().substring(0, 200) + "...");
            } else {
                if(searchHit.getHighlightFields().get("cvContent") != null){
                    searchResponse.setHighlight("..." + searchHit.getHighlightFields().get("cvContent").get(0) + "...");
                }
                else{
                    searchResponse.setHighlight("..." + searchHit.getHighlightFields().get("clContent").get(0) + "...");

                }
            }
            searchResponses.add(searchResponse);
        }
        return searchResponses;
    }
}
