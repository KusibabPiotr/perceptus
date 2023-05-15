package com.perceptus.library.weather.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class WeatherConfig {

    @Value("${openweathermap.api.key}")
    private String openWeatherApiKey;

    @Value("${openweathermap.now.endpoint}")
    private String openWeatherEndpoint;

    @Value("${openweathermap.now.city}")
    private String openWeatherCity;
}
