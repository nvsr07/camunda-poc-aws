package com.esrx.services.order.flow;


import com.esrx.services.order.messages.Message;
import com.esrx.services.order.messages.MessageSender;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderCompletedAdapter implements JavaDelegate {
  
  @Autowired
  private MessageSender messageSender;

  @Override
  public void execute(DelegateExecution context) throws Exception {
    String orderId = (String)context.getVariable("orderId");
    String correlationId = (String)context.getVariable("correlationId");
    String traceId = context.getProcessBusinessKey();

    messageSender.send( //
        new Message<Object>( //
            "OrderCompletedEvent", //
            traceId, //
            new OrderCompletedEventPayload() //
              .setOrderId(orderId)).setCorrelationId(correlationId));
  }

  

}
