package com.esrx.services.financial.transaction.messages;


import java.util.Date;

public class AuthorizedCreditCardPayload {


  private String creditCardNo;
  private Date expDate;
  private String ccCode;
  private Integer chargeAmount;
  private String cardHolderName;

  public String getCreditCardNo() {
    return creditCardNo;
  }

  public AuthorizedCreditCardPayload setCreditCardNo(String creditCardNo) {
    this.creditCardNo = creditCardNo;
    return this;
  }

  public Date getExpDate() {
    return expDate;
  }

  public AuthorizedCreditCardPayload setExpDate(Date expDate) {
    this.expDate = expDate;
    return this;
  }

  public String getCcCode() {
    return ccCode;
  }

  public AuthorizedCreditCardPayload setCcCode(String ccCode) {
    this.ccCode = ccCode;
    return this;
  }

  public Integer getChargeAmount() {
    return chargeAmount;
  }

  public AuthorizedCreditCardPayload setChargeAmount(Integer chargeAmount) {
    this.chargeAmount = chargeAmount;
    return this;
  }

  public String getCardHolderName() {
    return cardHolderName;
  }

  public AuthorizedCreditCardPayload setCardHolderName(String cardHolderName) {
    this.cardHolderName = cardHolderName;
    return this;
  }
}
