package com.esrx.services.order.flow;


import com.esrx.services.order.domain.Order;
import com.esrx.services.order.messages.Message;
import com.esrx.services.order.messages.MessageSender;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PendOrderAdapter implements JavaDelegate {
  
  @Autowired
  private MessageSender messageSender;


  @Override
  public void execute(DelegateExecution context) throws Exception {

    String orderId = (String)context.getVariable("orderId");
    String correlationId = (String)context.getVariable("correlationId");
    String traceId = context.getProcessBusinessKey();
    //TODO Mark Order Pending



  }


}
