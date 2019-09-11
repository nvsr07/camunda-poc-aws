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
public class FulfillOrderAdapter implements JavaDelegate {
  
  @Autowired
  private MessageSender messageSender;

  @Override
  public void execute(DelegateExecution context) throws Exception {
    Order order = null;
    String traceId = null;
    Faker faker = new Faker();
    traceId = context.getProcessBusinessKey();
    String orderId = (String)context.getVariable("orderId");
    String correlationId = (String)context.getVariable("correlationId");
    context.setVariable("isOrderMarkedPending", false);

  messageSender.send( //
        new Message<Object>( //
            "FulfillOrderCommand", //
            traceId, //
            new FulfillOrderCommandPayload() //
              .setRefId(orderId) //
                .setAmount(new Integer(faker.number().digits(4)))//
                .setLogisticsProvider(faker.name().name())//
                .setPickId(faker.idNumber().valid())//
                .setReason(faker.commerce().material())//
                .setRecipientAddress(faker.address().fullAddress())//
                .setRecipientName(faker.name().fullName())).setCorrelationId(correlationId));

  }


}
