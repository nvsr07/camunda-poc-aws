package com.esrx.services.order.flow.base;


import com.esrx.services.order.domain.Order;
import com.esrx.services.order.flow.FetchGoodsCommandPayload;
import com.esrx.services.order.messages.Message;
import com.esrx.services.order.messages.MessageSender;

import com.github.javafaker.Faker;
import org.camunda.bpm.engine.impl.pvm.delegate.ActivityExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * Alternative implementation if you prefer having send/receive in one single ServiceTask
 * which is often easier understood by "normal people"
 *
 */
@Component
public class FetchGoodsPubSubAdapter extends PublishSubscribeAdapter {
  
  @Autowired
  private MessageSender messageSender;

  @Override
  public void execute(ActivityExecution context) throws Exception {
    String orderId =  (String)context.getVariable("orderId");
    String traceId = context.getProcessBusinessKey();

    Faker faker = new Faker();


    // publish
    messageSender.send(new Message<>( //
            "FetchGoodsCommand", //
            traceId, //
            new FetchGoodsCommandPayload() //
              .setRefId(faker.idNumber().valid()) //
              .setItems(new ArrayList<>())));
    
    addMessageSubscription(context, "GoodsFetchedEvent");
  }
  
}
