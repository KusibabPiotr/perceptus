package com.perceptus.library.weather.client;

import com.perceptus.library.weather.config.WeatherConfig;
import com.perceptus.library.weather.dto.OutputWeatherDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WeatherClientTest {
    @InjectMocks
    private WeatherClient client;
    @Mock
    private WeatherConfig config;
    @Mock
    private RestTemplate template;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        client = new WeatherClient(template, config);
    }

    @Test
    public void testGetNowWeatherConditions() {
        //given
        OutputWeatherDto response = new OutputWeatherDto(new ArrayList<>(), null, null, null, null, null, 20101, "Wongrowo");

        when(template.getForObject(any(), any()))
                .thenReturn(response);
        when(config.getOpenWeatherEndpoint()).thenReturn("http://api.openweathermap.org/data/2.5/weather");
        when(config.getOpenWeatherApiKey()).thenReturn("ecb50481b265872817c44b68d5c142d5");
        when(config.getOpenWeatherCity()).thenReturn("Wongrowo");


        //when
        OutputWeatherDto result = client.getNowWeatherConditions();

        //then
        assertThat(result).isNotNull();
        assertThat(result.cityName()).isEqualTo("Wongrowo");
    }
}