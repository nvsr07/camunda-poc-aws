package com.esrx.services.fulfillment.flow;


import com.esrx.services.fulfillment.messages.Message;
import com.esrx.services.fulfillment.messages.MessageSender;
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
    String refId = (String) context.getVariable("refId");
    String correlationId = (String) context.getVariable("correlationId");
    String traceId = context.getProcessBusinessKey();

    messageSender.send( //
        new Message<Object>( //
            "OrderFulfilledEvent", //
            traceId, //
            new OrderFulfilledEventPayload() //
                .setRefId(refId))
    		.setCorrelationId(correlationId));
  }

}
