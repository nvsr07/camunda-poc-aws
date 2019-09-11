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
public class CancelOrderAdapter implements JavaDelegate {
  
  @Autowired
  private MessageSender messageSender;

  @Override
  public void execute(DelegateExecution context) throws Exception {
    Order order = null;
    String traceId = null;

    traceId = context.getProcessBusinessKey();
    String orderId = (String)context.getVariable("orderId");
    String refId = (String)context.getVariable("refId");
    String correlationId = (String)context.getVariable("correlationId");
    Faker faker = new Faker();

    messageSender.send( //
        new Message<Object>( //
            "CancelOrderCommand", //
            traceId, //
            new CancelOrderCommandPayload() //
                    .setRefId(refId) //
                    .setOrderId(orderId)//
                    .setCancelledDate(faker.date().birthday())//
                    .setReasonForCancellation(faker.commerce().promotionCode(10))).setCorrelationId(correlationId));

  }


}
