package com.perceptus.library.weather.controller;

import com.perceptus.library.weather.dto.OutputWeatherDto;
import com.perceptus.library.weather.service.WeatherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
class WeatherControllerTest {
    @InjectMocks
    private WeatherController controller;
    @Mock
    private WeatherService service;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void testRegisterEndpointOk() throws Exception {
        //given
        OutputWeatherDto wongrowo = new OutputWeatherDto(new ArrayList<>(), null, null, null, null, null, 20101, "Wongrowo");
        when(service.getDataFromApi()).thenReturn(wongrowo);

        //when&then
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/weather/getNow")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}