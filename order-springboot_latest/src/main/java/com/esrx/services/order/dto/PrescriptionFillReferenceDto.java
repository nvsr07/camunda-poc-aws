package com.esrx.services.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrescriptionFillReferenceDto extends ReferenceDto implements Serializable {
    private String fillNumber;
    // this is the prescription fill identified by its relativeId.
    private String relativeId;
}
