package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

@Document(indexName = "applicant")
@Setting(settingPath = "static/es-settings.json")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class IndexUnit {

    @Id
    @Field(type = FieldType.Keyword, index = false, store = true)
    private String id;

    @Field(type = FieldType.Text, analyzer = "serbian", store = true)
    private String name;

    @Field(type = FieldType.Text, analyzer = "serbian", store = true)
    private String surname;

    @Field(type = FieldType.Text,analyzer = "serbian", store = true)
    private String education;

    @Field(type = FieldType.Text, searchAnalyzer = "serbian",analyzer = "serbian")
    private String cvContent;

    @Field(type = FieldType.Text, searchAnalyzer = "serbian",analyzer = "serbian")
    private String clContent;

    @GeoPointField
    private GeoPoint location;
}
