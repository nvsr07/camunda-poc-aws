package com.esrx.services.inventory.reservation.messages;

import com.esrx.services.inventory.reservation.service.InventoryService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Component
@EnableBinding(Sink.class)
public class MessageListener {    
  
  @Autowired
  private MessageSender messageSender;
  
  @Autowired
  private InventoryService inventoryService;

  @StreamListener(target = Sink.INPUT, 
      condition="(headers['messageType']?:'')=='ReserveInventoryCommand'")
  @Transactional
  public void reserveInventoryCommandReceived(String messageJson) throws JsonParseException, JsonMappingException, IOException {
    Message<ReserveInventoryCommandPayload> message = new ObjectMapper().readValue(messageJson, new TypeReference<Message<ReserveInventoryCommandPayload>>(){});

    ReserveInventoryCommandPayload fetchGoodsCommand = message.getPayload();
    //String pickId = inventoryService.pickItems( //
     //   fetchGoodsCommand.getItems(), fetchGoodsCommand.getReason(), fetchGoodsCommand.getRefId());

    String pickId = "123456";
    messageSender.send( //
        new Message<InventoryReservedEventPayload>( //
            "InventoryReservedEvent", //
            message.getTraceId(), //
            new InventoryReservedEventPayload() //
              .setRefId(fetchGoodsCommand.getRefId())
              .setPickId(pickId))
        .setCorrelationId(message.getCorrelationId()));
  }
    
    
}
