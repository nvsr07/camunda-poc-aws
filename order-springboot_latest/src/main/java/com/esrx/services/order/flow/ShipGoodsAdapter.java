package com.esrx.services.order.flow;


import com.esrx.services.order.domain.Order;
import com.esrx.services.order.messages.Message;
import com.esrx.services.order.messages.MessageSender;

import com.github.javafaker.Faker;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ShipGoodsAdapter implements JavaDelegate {
  
  @Autowired
  private MessageSender messageSender;

  @Override
  public void execute(DelegateExecution context) throws Exception {
    String orderId = (String)context.getVariable("orderId");
    String pickId = (String)context.getVariable("pickId"); // TODO read from step before!
    String correlationId = (String)context.getVariable("correlationId");

    String traceId = context.getProcessBusinessKey();
    Faker faker = new Faker();

    messageSender.send(new Message<>( //
            "ShipGoodsCommand", //
            traceId, //
            new ShipGoodsCommandPayload() //
              .setRefId(faker.idNumber().valid())
              .setPickId(pickId) //
              .setRecipientName(faker.name().name()) //
              .setRecipientAddress(faker.address().fullAddress())).setCorrelationId(correlationId));
  }  

}
