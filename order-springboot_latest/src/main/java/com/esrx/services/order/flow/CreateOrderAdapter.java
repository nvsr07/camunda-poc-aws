package com.esrx.services.order.flow;


import com.esrx.services.order.domain.Order;
import com.esrx.services.order.dto.OrderDto;
import com.esrx.services.order.messages.Message;
import com.esrx.services.order.messages.MessageSender;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateOrderAdapter implements JavaDelegate {
  
  @Autowired
  private MessageSender messageSender;


  @Override
  public void execute(DelegateExecution context) throws Exception {
    Order order = null;
    String traceId = null;

    String orderId = (String)context.getVariable("orderId");
    String correlationId = (String)context.getVariable("correlationId");

    traceId = context.getProcessBusinessKey();
    messageSender.send( //
        new Message<>( //
            "OrderCreatedEvent", //
            traceId, //
            new OrderCreatedEventPayload() //
                    .setRefId(orderId) //
                    .setOrder(new OrderDto())).setCorrelationId(correlationId));

  }


}
