package com.perceptus.library.weather.service;

import com.perceptus.library.weather.client.WeatherClient;
import com.perceptus.library.weather.dto.OutputWeatherDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class WeatherServiceTest {
    @InjectMocks
    private WeatherService service;
    @Mock
    private WeatherClient client;

    @Test
    public void testGetDataFromApi() {
        //given
        OutputWeatherDto wongrowo = new OutputWeatherDto(new ArrayList<>(), null, null, null, null, null, 20101, "Wongrowo");
        when(client.getNowWeatherConditions()).thenReturn(wongrowo);
        //when
        OutputWeatherDto apiObject = service.getDataFromApi();
        //then
        assertThat(apiObject).isNotNull();
        assertThat(apiObject.cityName()).isEqualTo("Wongrowo");
    }
}