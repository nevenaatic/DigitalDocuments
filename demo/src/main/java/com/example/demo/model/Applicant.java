package com.example.demo.model;

import lombok.*;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Setting;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Document(indexName = "applicant")
@Setting(settingPath = "static/es-settings.json")
public class Applicant {

    @Id
    //@Field(type = FieldType.Keyword, index = false, store = true)
    private Integer id;

    @Field(type = FieldType.Text, analyzer = "serbian", store = true)
    private String name;

    @Field(type = FieldType.Text, analyzer = "serbian", store = true)
    private String surname;

    @Field(type = FieldType.Text, store = true)
    private Integer education;

    @Field(type = FieldType.Text, searchAnalyzer = "serbian",analyzer = "serbian")
    private String sadrzajCV;

    @Field(type = FieldType.Text, searchAnalyzer = "serbian",analyzer = "serbian")
    private String sadrzajPP;

    @GeoPointField
    private GeoPoint location;
}
