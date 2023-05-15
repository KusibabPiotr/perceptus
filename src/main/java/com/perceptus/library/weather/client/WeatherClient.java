package com.perceptus.library.weather.client;

import com.perceptus.library.weather.config.WeatherConfig;
import com.perceptus.library.weather.dto.OutputWeatherDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class WeatherClient {

    private final RestTemplate restTemplate;
    private final WeatherConfig weatherConfig;

    public OutputWeatherDto getNowWeatherConditions(){
        URI uri = UriComponentsBuilder.fromHttpUrl(weatherConfig.getOpenWeatherEndpoint())
                .queryParam("appid",weatherConfig.getOpenWeatherApiKey())
                .queryParam("q", weatherConfig.getOpenWeatherCity())
                .queryParam("units", "metric")
                .build().encode().toUri();

        return restTemplate.getForObject(uri,OutputWeatherDto.class);
    }
}
