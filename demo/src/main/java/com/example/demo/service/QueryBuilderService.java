package com.example.demo.service;

import com.example.demo.dto.GeoLocationDto;
import com.example.demo.dto.SimpleSearchDto;
import com.example.demo.model.CandidateLocation;
import lombok.AllArgsConstructor;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.GeoDistanceQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import static org.elasticsearch.index.query.QueryBuilders.matchPhraseQuery;
import static org.elasticsearch.index.query.QueryBuilders.multiMatchQuery;

@AllArgsConstructor
public class QueryBuilderService {

    private final ElasticsearchRestTemplate elasticsearchRestTemplate;

    public static NativeSearchQuery buildQueryApplicatnt(SimpleSearchDto dto) {
        String errorMessage = "";
        if (dto.getContent() == null || dto.getContent().equals("")) {
            errorMessage += "Field is empty";
        }
        if (dto.getContent() == null) {
            if (!errorMessage.equals("")) errorMessage += "\n";
            errorMessage += "Value is empty";
        }
        if (!errorMessage.equals("")) {
            throw new IllegalArgumentException(errorMessage);
        }

        return new NativeSearchQueryBuilder()
                .withQuery(multiMatchQuery(dto.getContent())
                        .field("name")
                        .field("surname")
                        .type(MultiMatchQueryBuilder.Type.BEST_FIELDS))
                //.withPageable(pageable)
                .withHighlightFields(
                        new HighlightBuilder.Field("cvContent").fragmentSize(20).numOfFragments(1)
                                .preTags("<b>").postTags("</b>"))
                .build();
    }

    public static NativeSearchQuery buildQueryApplicantPhrase(SimpleSearchDto dto) {
        String errorMessage = "";
        if (dto.getContent() == null || dto.getContent().equals("")) {
            errorMessage += "Field is empty";
        }
        if (dto.getContent() == null) {
            if (!errorMessage.equals("")) errorMessage += "\n";
            errorMessage += "Value is empty";
        }
        if (!errorMessage.equals("")) {
            throw new IllegalArgumentException(errorMessage);
        }

        return new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.boolQuery()
                        .should(new MultiMatchQueryBuilder(dto.getContent())
                                .field("name")
                                .field("surname")
                                .type(MultiMatchQueryBuilder.Type.PHRASE))
                )
                .withHighlightFields(
                        new HighlightBuilder.Field("cvContent").fragmentSize(250).numOfFragments(1)
                                .preTags("<b>").postTags("</b>"))
                .build();
    }

    public static NativeSearchQuery buildQueryEducation(SimpleSearchDto dto) {
        String errorMessage = "";
        if (dto.getContent() == null || dto.getContent().equals("")) {
            errorMessage += "Field is empty";
        }
        if (dto.getContent() == null) {
            if (!errorMessage.equals("")) errorMessage += "\n";
            errorMessage += "Value is empty";
        }
        if (!errorMessage.equals("")) {
            throw new IllegalArgumentException(errorMessage);
        }

        if(dto.getPhrase()){
            return new NativeSearchQueryBuilder()
                    .withQuery(matchPhraseQuery("education", dto.getContent())
                    )
                    .withHighlightFields(new HighlightBuilder.Field("cvContent").fragmentSize(250).preTags("<b>").postTags("</b>"))
                    .build();
        }else{
            return new NativeSearchQueryBuilder()
                    .withQuery(multiMatchQuery(dto.getContent())
                            .field("education")
                    )
                    .withHighlightFields(new HighlightBuilder.Field("cvContent").fragmentSize(250).preTags("<b>").postTags("</b>"))
                    .build();
        }
    }

    public static NativeSearchQuery buildQueryCV(SimpleSearchDto dto) {
        String errorMessage = "";
        if (dto.getContent() == null || dto.getContent().equals("")) {
            errorMessage += "Field is empty";
        }
        if (dto.getContent() == null) {
            if (!errorMessage.equals("")) errorMessage += "\n";
            errorMessage += "Value is empty";
        }
        if (!errorMessage.equals("")) {
            throw new IllegalArgumentException(errorMessage);
        }

        if(dto.getPhrase()){
            return new NativeSearchQueryBuilder()
                    .withQuery(matchPhraseQuery("cvContent", dto.getContent())
                    )
                    .withHighlightFields(new HighlightBuilder.Field("cvContent").fragmentSize(250).preTags("<b>").postTags("</b>"))
                    .build();
        }else{
            return new NativeSearchQueryBuilder()
                    .withQuery(multiMatchQuery(dto.getContent())
                            .field("cvContent")
                    )
                    .withHighlightFields(new HighlightBuilder.Field("cvContent").fragmentSize(250).preTags("<b>").postTags("</b>"))
                    .build();
        }
    }
    public static NativeSearchQuery buildQuerysearchByCoverLetter(SimpleSearchDto dto) {
        String errorMessage = "";
        if (dto.getContent() == null || dto.getContent().equals("")) {
            errorMessage += "Field is empty";
        }
        if (dto.getContent() == null) {
            if (!errorMessage.equals("")) errorMessage += "\n";
            errorMessage += "Value is empty";
        }
        if (!errorMessage.equals("")) {
            throw new IllegalArgumentException(errorMessage);
        }

        if(dto.getPhrase()){
            return new NativeSearchQueryBuilder()
                    .withQuery(matchPhraseQuery("clContent", dto.getContent())
                    )
                    .withHighlightFields(new HighlightBuilder.Field("clContent").fragmentSize(250).preTags("<b>").postTags("</b>"))
                    .build();
        }else{
            return new NativeSearchQueryBuilder()
                    .withQuery(multiMatchQuery(dto.getContent())
                            .field("clContent")
                    )
                    .withHighlightFields(new HighlightBuilder.Field("clContent").fragmentSize(250).preTags("<b>").postTags("</b>"))
                    .build();
        }
    }


    public static NativeSearchQuery buildQuerysearchByGeoLocation(GeoLocationDto dto, CandidateLocation location) throws Exception {
        String errorMessage = "";
        if (dto.getCity() == null || dto.getCity().equals("")) {
            errorMessage += "Field is empty";
        }

        if (dto.getRadius() == null) {
            errorMessage += "Field is empty";
        }

        if (!errorMessage.equals("")) {
            throw new IllegalArgumentException(errorMessage);
        }

        GeoDistanceQueryBuilder geoDistanceBuilder = new GeoDistanceQueryBuilder("location")
                .point(location.getLat(), location.getLon())
                .distance(dto.getRadius(),
                        DistanceUnit.parseUnit("km", DistanceUnit.KILOMETERS));

        System.out.print(geoDistanceBuilder.toString());

        return new NativeSearchQueryBuilder()
                .withFilter(geoDistanceBuilder)
                .withHighlightFields(new HighlightBuilder.Field("cvContent").fragmentSize(250).preTags("<b>").postTags("</b>"))
                .build();
    }


}
