package com.esrx.services.order.flow;


import com.esrx.services.order.domain.Order;
import com.esrx.services.order.messages.Message;
import com.esrx.services.order.messages.MessageSender;

import com.github.javafaker.Faker;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ShipOrderAdapter implements JavaDelegate {
  
  @Autowired
  private MessageSender messageSender;

  @Override
  public void execute(DelegateExecution context) throws Exception {
    String orderId = (String)context.getVariable("orderId");
    String correlationId = (String)context.getVariable("correlationId");
    String traceId = context.getProcessBusinessKey();
    Faker faker = new Faker();
    messageSender.send( //
        new Message<>( //
            "CreateShipmentCommand", //
            traceId, //
            new CreateShipmentCommandPayload() //
                    .setRefId(orderId) //
                    .setAmount(123)//
                    .setLogisticsProvider(faker.name().name())//
                    .setPickId(faker.idNumber().valid())//
                    .setReason(faker.commerce().material())//
                    .setRecipientAddress(faker.address().fullAddress())//
                    .setRecipientName(faker.name().fullName())).setCorrelationId(correlationId));
  }
}
