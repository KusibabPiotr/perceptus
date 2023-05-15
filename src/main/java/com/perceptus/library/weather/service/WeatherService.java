package com.perceptus.library.weather.service;

import com.perceptus.library.weather.client.WeatherClient;
import com.perceptus.library.weather.dto.OutputWeatherDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WeatherService {
    private final WeatherClient weatherClient;

    public OutputWeatherDto getDataFromApi(){
        return  weatherClient.getNowWeatherConditions();
    }
}
