package com.perceptus.library.weather.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record WindDto(
        @JsonProperty(value = "speed")
        double speed,
        @JsonProperty(value = "deg")
        double direction
) {
}
