package com.esrx.services.pharmacy.claim.messages;

public class AdjudicateClaimCommandPayload {

  public String getPharmacyId() {
    return pharmacyId;
  }

  public AdjudicateClaimCommandPayload setPharmacyId(String pharmacyId) {
    this.pharmacyId = pharmacyId;
    return this;
  }

  public String getClaimCenterNo() {
    return claimCenterNo;
  }

  public AdjudicateClaimCommandPayload setClaimCenterNo(String claimCenterNo) {
    this.claimCenterNo = claimCenterNo;
    return this;
  }

  public String getClaimCenterName() {
    return claimCenterName;
  }

  public AdjudicateClaimCommandPayload setClaimCenterName(String claimCenterName) {
    this.claimCenterName = claimCenterName;
    return this;
  }

  public String getProviderNumber() {
    return providerNumber;
  }

  public AdjudicateClaimCommandPayload setProviderNumber(String providerNumber) {
    this.providerNumber = providerNumber;
    return this;
  }

  public String getNpi() {
    return npi;
  }

  public AdjudicateClaimCommandPayload setNpi(String npi) {
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

  public AdjudicateClaimCommandPayload setRefId(String refId) {
    this.refId = refId;
    return this;
  }

  private String refId;


}
