package com.esrx.services.order.flow;


import com.esrx.services.order.messages.Message;
import com.esrx.services.order.messages.MessageSender;

import com.github.javafaker.Faker;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AttachMembershipAdapter implements JavaDelegate {

  @Autowired
  private MessageSender messageSender;

  @Override
  public void execute(DelegateExecution context) throws Exception {
    String traceId = context.getProcessBusinessKey();
    String orderId = (String)context.getVariable("orderId");
    String refId = (String) context.getVariable("refId");
    String correlationId = (String)context.getVariable("correlationId");
    Faker faker = new Faker();

  //  context.setVariable("isMembershipAttached", faker.bool().bool());
    context.setVariable("isMembershipAttached", true);

    messageSender.send( //
        new Message<Object>( //
            "AttachMembershipCommand", //
            traceId, //
            new AttachMembershipCommandPayload() //
              .setPatientName(faker.name().fullName())//
                .setPatientNumber(faker.number().digits(5))//
                    .setRefId(refId)).setCorrelationId(correlationId));

  }


}
