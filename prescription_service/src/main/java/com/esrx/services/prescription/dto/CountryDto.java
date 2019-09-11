package com.esrx.services.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountryDto extends BaseDto implements Serializable {
    private String name;
    private String iso2Code;
    private String iso3Code;
    private String description;
}
