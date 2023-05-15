package com.perceptus.library.weather.controller;

import com.perceptus.library.weather.dto.OutputWeatherDto;
import com.perceptus.library.weather.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/weather")
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping("/getNow")
    public OutputWeatherDto getWeatherForNow(){
        return weatherService.getDataFromApi();
    }
}
