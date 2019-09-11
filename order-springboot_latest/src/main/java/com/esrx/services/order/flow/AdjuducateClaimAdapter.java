package com.esrx.services.order.flow;


import com.esrx.services.order.messages.Message;
import com.esrx.services.order.messages.MessageSender;

import com.github.javafaker.Faker;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AdjuducateClaimAdapter implements JavaDelegate {

  @Autowired
  private MessageSender messageSender;


  @Override
  public void execute(DelegateExecution context) throws Exception {
    String traceId = context.getProcessBusinessKey();
    String orderId = (String)context.getVariable("orderId");
    String correlationId = (String)context.getVariable("correlationId");
    String refId = (String)context.getVariable("refId");
    Faker faker = new Faker();

    context.setVariable("isClaimAdjudicated", true);

    messageSender.send( //
        new Message<Object>( //
            "AdjudicateClaimCommand", //
            traceId, //
            new AdjuducateClaimPayload() //
              .setClaimCenterName(faker.address().cityName())//
                .setClaimCenterNo(faker.number().digits(5))//
                    .setNpi(faker.number().digits(10))//
                    .setPharmacyId(faker.idNumber().valid())//
                    .setRefId(refId)//
                    .setProviderNumber(faker.number().digits(8))).setCorrelationId(correlationId));

  }


}
