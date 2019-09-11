package com.esrx.services.order.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "#{@orderCollectionName}")
public class OrderDto extends BaseDto implements Serializable {

    @Id
    private UUID resourceId;

    private String orderId;
    private String fulfillmentCenterId;
    private String orderFulfillmentId;

    private PersonReferenceDto forPerson;
    private PersonNameDto forName;
    private PersonNameDto careOfName;

    private PostalAddressDto postalAddress;

    private Date needByDate;
    private Date promisedDate;
    private Date shipByDate;

    private Date orderCreatedDateTime;

    private CodeDto lineOfBusinessCode;

    private List<StatusDto> statuses = new ArrayList<>();

    private List<StatusDto> financialStatuses = new ArrayList<>();

    private List<ItemDto> items = new ArrayList<>();

    //Audit Fields
    private Date createDateTime;
    private String createdBy;
    private Date updateDateTime;
    private String updatedBy;

    // tenant ID
    private Long tenantId;

    //Source DateTime
    private Date eventSourceDateTime;

    private List<StoreKey> secondaryKeys;

}
