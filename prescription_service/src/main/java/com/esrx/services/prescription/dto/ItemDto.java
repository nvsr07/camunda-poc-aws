package com.esrx.services.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto extends BaseDto implements Serializable {
    private UUID relativeId;

    private String productTypeCode;

    private PrescriptionFillReferenceDto prescriptionFill;

    private OrderRequestReferenceDto orderRequest;

    private String externalOrderId;

    private SupplyReferenceDto supply;

    private List<StatusDto> statuses = new ArrayList<>();

    private List<StatusDto> financialStatuses = new ArrayList<>();

    private List<PaymentDto> payments = new ArrayList<>();

    private String subInvoiceNum;

    private boolean ndi99;

    private Date packDateTime;

    private String labelPrescriptionNumber;

    private CodeDto lineOfBusinessCode;

    //Source DateTimes
    private Date rflSourceDateTime;
    private Date cflSourceDateTime;
    private Date ndiSourceDateTime;
    private Date pendSourceDateTime;

}
