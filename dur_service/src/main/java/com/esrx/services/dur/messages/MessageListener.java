package com.esrx.services.dur.messages;

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
      condition="(headers['messageType']?:'')=='RunDURCommand'")
  @Transactional
  public void runDURCommandReceived(String messageJson) throws JsonParseException, JsonMappingException, IOException {
    Message<RunDURCommandPayload> message = new ObjectMapper().readValue(messageJson, new TypeReference<Message<RunDURCommandPayload>>(){});
    RunDURCommandPayload retrievePaymentCommand = message.getPayload();
    
    System.out.println("Retrieve payment: " + retrievePaymentCommand.getLogisticsProvider() + " for " + retrievePaymentCommand.getRefId());
    
    camunda.getRuntimeService().createMessageCorrelation(message.getMessageType()) //
      .processInstanceBusinessKey(message.getTraceId())
      .setVariable("logisticsProvider", retrievePaymentCommand.getLogisticsProvider()) //
      .setVariable("recipientAddress", retrievePaymentCommand.getRecipientAddress()) //
      .setVariable("refId", retrievePaymentCommand.getRefId()) //
      .setVariable("correlationId", message.getCorrelationId()) //
      .correlateWithResult();    
  }
    
    
}
