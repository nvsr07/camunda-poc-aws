package com.esrx.services.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostalAddressDto extends BaseDto implements Serializable {
    private List<String> streetAddress;
    private String officeSuite;
    private String apartmentNumber;
    private String postOfficeBoxNumber;
    private String city;
    private StateDto state;
    private String postalCode;
    private CountryDto country;
}
