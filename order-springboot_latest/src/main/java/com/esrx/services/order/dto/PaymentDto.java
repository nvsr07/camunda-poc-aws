package com.esrx.services.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto extends BaseDto implements Serializable {
    private UUID relativeId;

    private String planId;

    private String groupId;

    private Boolean patientBillIndicator;

    private AmountDto owedAmount;
}
