package com.example.demo.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;
import org.springframework.data.elasticsearch.annotations.GeoPointField;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Document(indexName = "applicant")
public class IndexUnit {

    @Id
    @Field(type = FieldType.Keyword, index = false, store = true)
    private String id;

    @Field(type = FieldType.Text, store = true, searchAnalyzer = "serbian",analyzer = "serbian")
    private String name;

    @Field(type = FieldType.Text,searchAnalyzer = "serbian", analyzer = "serbian")
    private String surname;

    @Field(type = FieldType.Text, store = true,searchAnalyzer = "serbian", analyzer = "serbian")
    private String education;

    @GeoPointField
    private GeoPoint location;

    @Field(type = FieldType.Text, store = true,searchAnalyzer = "serbian", analyzer = "serbian")
    private String cvContent;

    @Field(type = FieldType.Text, store = true,searchAnalyzer = "serbian", analyzer = "serbian")
    private String clContent;
}
