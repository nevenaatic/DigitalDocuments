package com.example.demo.controller;

import com.example.demo.dto.SearchResponseDto;
import com.example.demo.model.Location;
import com.example.demo.service.CandidateLocationService;
import lombok.AllArgsConstructor;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.GeoDistanceQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pretraga")
@AllArgsConstructor
public class PretragaController {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    RestHighLevelClient client;
    @Autowired
    CandidateLocationService lokacijaESService ;

    @GetMapping(path = "/geoSpacing/{city}/{radius}", produces = "application/json")
    public @ResponseBody
    ResponseEntity getUsersByLocation(@PathVariable("city") String city, @PathVariable("radius") int radius) throws Exception{
        Location location = lokacijaESService.getLocationFromAddress(city);
        System.out.println(location.getLat());
        System.out.println(location.getLon());
//        BoolQueryBuilder qb = new BoolQueryBuilder();
//        qb.must(QueryBuilders.matchAllQuery());
//        qb.must(QueryBuilders.geoDistanceQuery("location").distance(radius + "km").point(location.getLon(),location.getLat()));


        GeoDistanceQueryBuilder geoDistanceBuilder = new GeoDistanceQueryBuilder("location")
                .point(location.getLat(), location.getLon())
                .distance(radius,
                        DistanceUnit.parseUnit("km", DistanceUnit.KILOMETERS));

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(geoDistanceBuilder);

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.source(sourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

        List<SearchResponseDto> searchResults = new ArrayList<>();

        for(org.elasticsearch.search.SearchHit hit : searchResponse.getHits()) {
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            SearchResponseDto podaciDTO = new SearchResponseDto();
            podaciDTO.setId((sourceAsMap.get("id").toString()));
            podaciDTO.setFirstName(sourceAsMap.get("ime").toString());
            podaciDTO.setLastName(sourceAsMap.get("prezime").toString());
            podaciDTO.setEducation(sourceAsMap.get("obrazovanje").toString());
            podaciDTO.setHighlight("");

            searchResults.add(podaciDTO);
        }

        System.out.println(searchResults);

        return new ResponseEntity(searchResults, HttpStatus.OK);
    }
}
