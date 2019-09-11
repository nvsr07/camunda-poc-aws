package com.esrx.services.order.flow;


import com.esrx.services.order.domain.Order;
import com.esrx.services.order.messages.Message;
import com.esrx.services.order.messages.MessageSender;

import com.github.javafaker.Faker;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class AuthorizedCreditCardAdapter implements JavaDelegate {
  
  @Autowired
  private MessageSender messageSender;

  @Override
  public void execute(DelegateExecution context) throws Exception {
    Order order = null;
    String traceId = null;


      traceId = context.getProcessBusinessKey();
      Faker faker = new Faker();

      context.setVariable("isOrderMarkedPending", false);

     String correlationId = (String)context.getVariable("correlationId");

      messageSender.send( //
        new Message<Object>( //
            "AuthorizeCreditCardCommand", //
            traceId, //
            new AuthorizedCreditCardPayload() //
              .setCardHolderName(faker.name().name())
                    .setCcCode(faker.number().digits(3))
                    .setChargeAmount(new Integer(faker.number().digits(5)))
                    .setCreditCardNo(faker.number().digits(16))
                    .setExpDate(faker.date().future(200, TimeUnit.DAYS))).setCorrelationId(correlationId));


  }


}
