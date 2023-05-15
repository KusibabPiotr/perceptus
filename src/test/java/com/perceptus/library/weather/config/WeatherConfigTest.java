package com.perceptus.library.weather.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class WeatherConfigTest {

    @Autowired
    private WeatherConfig config;

    @Test
    public void testValues(){
        //given
        String resultKey = "ecb50481b265872817c44b68d5c142d5";
        String resultUrl = "http://api.openweathermap.org/data/2.5/weather";
        String resultCity = "Warsaw";
        //when
        String apiKey = config.getOpenWeatherApiKey();
        String apiUrl = config.getOpenWeatherEndpoint();
        String apiCity = config.getOpenWeatherCity();
        //then
        assertThat(resultKey).isEqualTo(apiKey);
        assertThat(resultUrl).isEqualTo(apiUrl);
        assertThat(resultCity).isEqualTo(apiCity);
    }
}