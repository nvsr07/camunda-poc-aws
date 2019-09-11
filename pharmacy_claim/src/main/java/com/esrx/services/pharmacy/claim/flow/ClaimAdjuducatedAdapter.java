package com.esrx.services.pharmacy.claim.flow;

import com.esrx.services.pharmacy.claim.messages.Message;
import com.esrx.services.pharmacy.claim.messages.MessageSender;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClaimAdjuducatedAdapter implements JavaDelegate {

  @Autowired
  private MessageSender messageSender;

  @Override
  public void execute(DelegateExecution context) throws Exception {
    String refId = (String) context.getVariable("refId");
    String correlationId = (String) context.getVariable("correlationId");
    String traceId = context.getProcessBusinessKey();

    messageSender.send( //
        new Message<Object>( //
            "ClaimAdjuducatedEvent", //
            traceId, //
            new ClaimAdjuducatedPayload() //
                .setRefId(refId))
    		.setCorrelationId(correlationId));
  }

}
