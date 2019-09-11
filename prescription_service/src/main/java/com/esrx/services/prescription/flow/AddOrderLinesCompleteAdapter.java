package com.esrx.services.prescription.flow;


import com.esrx.services.prescription.messages.Message;
import com.esrx.services.prescription.messages.MessageSender;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddOrderLinesCompleteAdapter implements JavaDelegate {

  @Autowired
  private MessageSender messageSender;

  @Override
  public void execute(DelegateExecution context) throws Exception {
    String refId = (String) context.getVariable("refId");
    String correlationId = (String) context.getVariable("correlationId");
    String traceId = context.getProcessBusinessKey();

    messageSender.send( //
        new Message<Object>( //
            "CreateFillCompleteEvent", //
            traceId, //
            new AddOrderLinesCompleteEventPayload() //
                .setRefId(refId))
    		.setCorrelationId(correlationId));
  }

}
