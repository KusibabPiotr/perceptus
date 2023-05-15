package com.perceptus.library.weather.dto;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
public class InputDataParamsDto {

    private String city;
    private String units;

    public String getCity() {
        return city;
    }

    public String getUnits() {
        return units;
    }
}