package com.esrx.services.prescription.messages;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.camunda.bpm.engine.ProcessEngine;
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
  private ProcessEngine camunda;
  
  @Autowired
  private MessageSender messageSender;

  @StreamListener(target = Sink.INPUT, 
      condition="(headers['messageType']?:'')=='CreateShipmentCommand'")
  @Transactional
  public void retrievePaymentCommandReceived(String messageJson) throws JsonParseException, JsonMappingException, IOException {
    Message<CreateShipmentCommandPayload> message = new ObjectMapper().readValue(messageJson, new TypeReference<Message<CreateShipmentCommandPayload>>(){});
    CreateShipmentCommandPayload createShipmentCommand = message.getPayload();
    
    System.out.println("Retrieve payment: " + createShipmentCommand.getAmount() + " for " + createShipmentCommand.getRefId());
    
    camunda.getRuntimeService().createMessageCorrelation(message.getMessageType()) //
      .processInstanceBusinessKey(message.getTraceId())
      .setVariable("amount", createShipmentCommand.getAmount()) //
      .setVariable("remainingAmount", createShipmentCommand.getAmount()) //
      .setVariable("refId", createShipmentCommand .getRefId()) //
      .setVariable("correlationId", message.getCorrelationId()) //
      .correlateWithResult();    
  }
    
    
}
