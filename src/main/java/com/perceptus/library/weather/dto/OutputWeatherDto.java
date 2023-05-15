package com.perceptus.library.weather.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record OutputWeatherDto(
        @JsonProperty(value = "weather")
        List<WeatherDescriptionDto> weatherDescriptionDtoList,
        @JsonProperty(value = "main")
        WeatherTemperaturePressureHumadityDto weatherTemperaturePressureHumadityDto,
        @JsonProperty(value = "wind")
        WindDto windDto,
        @JsonProperty(value = "clouds")
        CloudsDto cloudsDto,
        @JsonProperty(value = "rain")
        RainDto rainDto,
        @JsonProperty(value = "snow")
        SnowDto snowDto,
        @JsonProperty(value = "id")
        int cityId,
        @JsonProperty(value = "name")
        String cityName
) {
}
