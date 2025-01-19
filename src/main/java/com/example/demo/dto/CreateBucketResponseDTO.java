package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateBucketResponseDTO {

    @JsonProperty("location")
    private String location;

    // Default constructor
    public CreateBucketResponseDTO() {}

    // Constructor
    public CreateBucketResponseDTO(String location) {
        this.location = location;
    }

    // Getter and Setter
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "CreateBucketResponseDTO{" +
                "location='" + location + '\'' +
                '}';
    }
}

