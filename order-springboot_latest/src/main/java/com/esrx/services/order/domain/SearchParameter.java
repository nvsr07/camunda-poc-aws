package com.esrx.services.order.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by EI6323 on 3/26/2018.
 */
@Data
public class SearchParameter implements Serializable{

    private static final long serialVersionUID = 1L;

    private String orderFulfillmentId;

    private String[] lineOfBusinessCodes;

    private String rxResourceId;

    private String rxFillRelativeId;

    private String fillNumber;

    private String[] personResourceIds;

    private List<Long> tenantIds;
}
