package com.example.demo.controller;

import com.example.demo.dto.AdvancedSearchRequestsDto;
import com.example.demo.dto.GeoLocationDto;
import com.example.demo.dto.SimpleSearchDto;
import com.example.demo.model.Location;
import com.example.demo.service.CandidateLocationService;
import com.example.demo.service.QueryBuilderService;
import com.example.demo.service.SearchService;
import lombok.AllArgsConstructor;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            query = QueryBuilderService.buildQueryApplicant(dto);
        }
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
        NativeSearchQuery query = QueryBuilderService.buildQuerySearchByCoverLetter(dto);

        return new ResponseEntity<>(searchService.simpleSearch(query), HttpStatus.OK);
    }


    @PostMapping(value = "/advanced")
    public ResponseEntity<?> advancedSearch(@RequestBody List<AdvancedSearchRequestsDto> dto) throws Exception {
        NativeSearchQuery query = QueryBuilderService.buildQuerySearchAdvanced(dto);

        return new ResponseEntity<>(searchService.simpleSearch(query), HttpStatus.OK);
    }

    @PostMapping(value = "/location")
    public ResponseEntity<?> searchByGeoLocation(@RequestBody GeoLocationDto dto) throws Exception {
        Location location = locationService.getLocationFromAddress(dto.getCity());
        NativeSearchQuery query = QueryBuilderService.buildQuerySearchByGeoLocation(dto, location);

        return new ResponseEntity<>(searchService.simpleSearch(query), HttpStatus.OK);
    }
}
