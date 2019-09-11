package com.esrx.services.order.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplyReferenceDto extends ReferenceDto implements Serializable {
    private StoreKey storeKeySet;
    private String parentPrescriptionFillRelativeId;
}
