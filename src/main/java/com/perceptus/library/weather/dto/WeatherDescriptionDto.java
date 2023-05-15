package com.perceptus.library.weather.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record WeatherDescriptionDto(
        @JsonProperty(value = "main")
        String name,
        @JsonProperty(value = "description")
        String description
) {
}
