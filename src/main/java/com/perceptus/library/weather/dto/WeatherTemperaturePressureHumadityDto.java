package com.perceptus.library.weather.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record WeatherTemperaturePressureHumadityDto(
        @JsonProperty(value = "temp")
        double temperature,
        @JsonProperty(value = "feels_like")
        double perceptibleTemperature,
        @JsonProperty(value = "pressure")
        int pressure,
        @JsonProperty(value = "humidity")
        int humidity
) {
}
