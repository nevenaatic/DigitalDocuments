package com.example.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.GeoPointField;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

    @Document(indexName = "podaci", type = "aplikacije")
    public class PodaciES {
        @Id
        private Integer id;

        @Field(type = FieldType.Text)
        private String ime;

        @Field(type = FieldType.Text)
        private String prezime;

        @Field(type = FieldType.Text, searchAnalyzer = "serbian",analyzer = "serbian")
        private String obrazovanje;
        @Field(type = FieldType.Text, searchAnalyzer = "serbian",analyzer = "serbian")
        private String sadrzajCV;

        @Field(type = FieldType.Text, searchAnalyzer = "serbian",analyzer = "serbian")
        private String sadrzajPP;

        @GeoPointField
        private GeoPoint location;

        public PodaciES() {
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getIme() {
            return ime;
        }

        public void setIme(String ime) {
            this.ime = ime;
        }

        public String getPrezime() {
            return prezime;
        }

        public void setPrezime(String prezime) {
            this.prezime = prezime;
        }

        public String getSadrzajCV() {
            return sadrzajCV;
        }

        public void setSadrzajCV(String sadrzajCV) {
            if(sadrzajCV == null){
                this.sadrzajCV = "";
            }
            this.sadrzajCV = sadrzajCV;
        }

        public String getSadrzajPP() {
            return sadrzajPP;
        }

        public void setSadrzajPP(String sadrzajPP) {
            if(sadrzajPP == null){
                this.sadrzajPP = "";
            }
            this.sadrzajPP = sadrzajPP;
        }

        public String getObrazovanje() {
            return obrazovanje;
        }

        public void setObrazovanje(String obrazovanje) {
            this.obrazovanje = obrazovanje;
        }

        public GeoPoint getLocation() {
            return location;
        }

        public void setLocation(GeoPoint location) {
            this.location = location;
        }

    }
