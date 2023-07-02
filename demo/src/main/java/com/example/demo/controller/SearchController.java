package com.example.demo.controller;

import com.example.demo.dto.AdvancedSearchRequestsDto;
import com.example.demo.dto.GeoLocationDto;
import com.example.demo.dto.SearchResponseDto;
import com.example.demo.dto.SimpleSearchDto;
import com.example.demo.model.Location;
import com.example.demo.service.CandidateLocationService;
import com.example.demo.service.QueryBuilderService;
import com.example.demo.service.SearchService;
import lombok.AllArgsConstructor;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/search")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class SearchController {

    private final SearchService searchService;
    private final CandidateLocationService locationService;

    @Autowired
    RestHighLevelClient client;

    @PostMapping(value = "/applicant")
    public ResponseEntity<?> simpleSearchApplicant(@RequestBody SimpleSearchDto dto) {
        NativeSearchQuery query;

        if(dto.getPhrase()){
            query = QueryBuilderService.buildQueryApplicantPhrase(dto);
        }else{
            query = QueryBuilderService.buildQueryApplicatnt(dto);
        }
System.out.print(query);
        return new ResponseEntity<>(searchService.simpleSearch(query), HttpStatus.OK);
    }

    @PostMapping(value = "/education")
    public ResponseEntity<?> simpleSearchEducation(@RequestBody SimpleSearchDto dto) {
        NativeSearchQuery query = QueryBuilderService.buildQueryEducation(dto);

        return new ResponseEntity<>(searchService.simpleSearch(query), HttpStatus.OK);
    }

    @PostMapping(value = "/cv")
    public ResponseEntity<?> searchByCV(@RequestBody SimpleSearchDto dto) {
        NativeSearchQuery query = QueryBuilderService.buildQueryCV(dto);

        return new ResponseEntity<>(searchService.simpleSearch(query), HttpStatus.OK);
    }

    @PostMapping(value = "/cl")
    public ResponseEntity<?> searchByCoverLetter(@RequestBody SimpleSearchDto dto) {
        NativeSearchQuery query = QueryBuilderService.buildQuerysearchByCoverLetter(dto);

        return new ResponseEntity<>(searchService.simpleSearch(query), HttpStatus.OK);
    }


    @PostMapping(value = "/advanced")
    public ResponseEntity<?> advancedSearch(@RequestBody List<AdvancedSearchRequestsDto> dto) throws Exception {
        NativeSearchQuery query = QueryBuilderService.buildQuerysearchAdvanced(dto);

        return new ResponseEntity<>(searchService.simpleSearch(query), HttpStatus.OK);
    }

  /*  @PostMapping(value = "/location")
    public ResponseEntity<?> searchByGeoLocation(@RequestBody GeoLocationDto dto) throws Exception {
        Location location = locationService.getLocationFromAddress(dto.getCity());

        BoolQueryBuilder qb = new BoolQueryBuilder();
        qb.must(QueryBuilders.matchAllQuery());
        qb.must(QueryBuilders.geoDistanceQuery("location").distance(dto.getRadius() + "km").point(location.getLon(),location.getLat()));

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(qb);
        SearchRequest searchRequest = new SearchRequest("podaci");
        searchRequest.source(sourceBuilder);
    System.out.print(searchRequest);

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

        List<SearchResponseDto> searchResult = new ArrayList<>();

        for(SearchHit hit: searchResponse.getHits()){
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            SearchResponseDto searchResponseDto = new SearchResponseDto();
            searchResponseDto.setId(sourceAsMap.get("id").toString());
            searchResponseDto.setFirstName(sourceAsMap.get("name").toString());
        searchResponseDto.setLastName(sourceAsMap.get("surname").toString());
        searchResponseDto.setAddress(sourceAsMap.get("address").toString());
            searchResponseDto.setEducation(sourceAsMap.get("education").toString());
            searchResponseDto.setHighlight("");
            searchResult.add(searchResponseDto);

        }System.out.println(searchResult);
        return new ResponseEntity<>(searchResult, HttpStatus.OK);
    } */

        @PostMapping(value = "/location")
    public ResponseEntity<?> searchByGeoLocation(@RequestBody GeoLocationDto dto) throws Exception {
        Location location = locationService.getLocationFromAddress(dto.getCity());
        NativeSearchQuery query = QueryBuilderService.buildQuerysearchByGeoLocation(dto, location);

        return new ResponseEntity<>(searchService.simpleSearch(query), HttpStatus.OK);
    }
}
