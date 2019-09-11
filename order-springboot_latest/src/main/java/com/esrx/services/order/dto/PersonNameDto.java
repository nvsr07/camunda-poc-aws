package com.esrx.services.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonNameDto extends BaseDto implements Serializable {
    private String first;
    private String middle;
    private String last;
    private String preferredFirst;
    private String prefix;
    private String[] professionalSuffixes;
    private String generationalSuffix;
}
