package com.esrx.services.financial.transaction.flow;


import com.esrx.services.financial.transaction.messages.Message;
import com.esrx.services.financial.transaction.messages.MessageSender;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreditCardAuthorizedAdapter implements JavaDelegate {

  @Autowired
  private MessageSender messageSender;

  @Override
  public void execute(DelegateExecution context) throws Exception {
    String refId = (String) context.getVariable("refId");
    String correlationId = (String) context.getVariable("correlationId");
    String traceId = context.getProcessBusinessKey();

    messageSender.send( //
        new Message<Object>( //
            "CreditCardAuthorizedEvent", //
            traceId, //
            new CreditCardAuthorizedEventPayload() //
                .setRefId(refId))
    		.setCorrelationId(correlationId));
  }

}
