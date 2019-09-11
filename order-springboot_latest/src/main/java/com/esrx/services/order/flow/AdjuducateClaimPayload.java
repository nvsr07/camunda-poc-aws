package com.esrx.services.order.flow;


import com.esrx.services.order.dto.ItemDto;

import java.util.ArrayList;
import java.util.List;

public class AdjuducateClaimPayload {

  public String getPharmacyId() {
    return pharmacyId;
  }

  public AdjuducateClaimPayload setPharmacyId(String pharmacyId) {
    this.pharmacyId = pharmacyId;
    return this;
  }

  public String getClaimCenterNo() {
    return claimCenterNo;
  }

  public AdjuducateClaimPayload setClaimCenterNo(String claimCenterNo) {
    this.claimCenterNo = claimCenterNo;
    return this;
  }

  public String getClaimCenterName() {
    return claimCenterName;
  }

  public AdjuducateClaimPayload setClaimCenterName(String claimCenterName) {
    this.claimCenterName = claimCenterName;
    return this;
  }

  public String getProviderNumber() {
    return providerNumber;
  }

  public AdjuducateClaimPayload setProviderNumber(String providerNumber) {
    this.providerNumber = providerNumber;
    return this;
  }

  public String getNpi() {
    return npi;
  }

  public AdjuducateClaimPayload setNpi(String npi) {
    this.npi = npi;
    return this;
  }

  private String pharmacyId;
  private String claimCenterNo;
  private String claimCenterName;
  private String providerNumber;
  private String npi;

  public String getRefId() {
    return refId;
  }

  public AdjuducateClaimPayload setRefId(String refId) {
    this.refId = refId;
    return this;
  }

  private String refId;



}
