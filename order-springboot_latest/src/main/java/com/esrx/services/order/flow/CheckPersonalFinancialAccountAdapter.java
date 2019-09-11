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
public class CheckPersonalFinancialAccountAdapter implements JavaDelegate {
  
  @Autowired
  private MessageSender messageSender;

  @Override
  public void execute(DelegateExecution context) throws Exception {
    Order order = null;
    String traceId = null;


    traceId = context.getProcessBusinessKey();

     Faker faker = new Faker();

    context.setVariable("isCheckFinancePassed", true);
    String correlationId = (String)context.getVariable("correlationId");
    messageSender.send( //
            new Message<Object>( //
                    "PersonalFinancialAccountChekedEvent", //
                    traceId, //
                    new RetrievePaymentCommandPayload() //
                            .setRefId(faker.idNumber().valid()) //
                            .setAmount(new Integer(faker.number().digits(4)))).setCorrelationId(correlationId));
  }


}
