package com.perceptus.library.weather.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OutputWeatherDto {

    @JsonProperty(value = "weather")
    private List<WeatherDescriptionDto> weatherDescriptionDtoList = new ArrayList<>();
    @JsonProperty(value = "main")
    private WeatherTemperaturePressureHumadityDto weatherTemperaturePressureHumadityDto;
    @JsonProperty(value = "wind")
    private WindDto windDto;
    @JsonProperty(value = "clouds")
    private CloudsDto cloudsDto;
    @JsonProperty(value = "rain")
    private RainDto rainDto;
    @JsonProperty(value = "snow")
    private SnowDto snowDto;
    @JsonProperty(value = "id")
    private int cityId;
    @JsonProperty(value = "name")
    private String cityName;
}
