package com.esrx.services.order.flow;


import com.esrx.services.order.domain.Order;
import com.esrx.services.order.messages.Message;
import com.esrx.services.order.messages.MessageSender;

import com.github.javafaker.Faker;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class ReserveInventoryAdapter implements JavaDelegate {
  
  @Autowired
  private MessageSender messageSender;


  @Override
  public void execute(DelegateExecution context) throws Exception {
    String orderId = (String)context.getVariable("orderId");
    String correlationId = (String)context.getVariable("correlationId");
    String traceId = context.getProcessBusinessKey();
    Faker faker = new Faker();

    List<String> itemNos = new ArrayList<>();
    itemNos.add(faker.number().digits(6));
    itemNos.add(faker.number().digits(6));
    itemNos.add(faker.number().digits(6));

    List<Date> orderPlacedDates = new ArrayList<Date>();
    orderPlacedDates.add(faker.date().birthday());
    orderPlacedDates.add(faker.date().birthday());
    orderPlacedDates.add(faker.date().birthday());

    List<Integer> quantity = new ArrayList<Integer>();
    quantity.add(new Integer(faker.number().digits(2)));
    quantity.add(new Integer(faker.number().digits(2)));
    quantity.add(new Integer(faker.number().digits(2)));

    //context.setVariable("isitOutofStock", faker.bool().bool());
    context.setVariable("isitOutofStock", false);
    messageSender.send( //
        new Message<Object>( //
            "ReserveInventoryCommand", //
            traceId, //
            new ReserveInventoryPayload() //
                    .setRefId(orderId) //
                    .setItemNos(itemNos)//
                    .setOrderPlacedDate(orderPlacedDates)//
                    .setQuantity(quantity)).setCorrelationId(correlationId));

  }

}
