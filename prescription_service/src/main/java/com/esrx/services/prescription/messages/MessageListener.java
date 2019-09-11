package com.esrx.services.prescription.messages;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.camunda.bpm.engine.ProcessEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;


import java.io.IOException;

@Component
@EnableBinding(Sink.class)
public class MessageListener {  
  
  @Autowired
  private ProcessEngine camunda;
  
  @Autowired
  private MessageSender messageSender;

  @StreamListener(target = Sink.INPUT, 
      condition="(headers['messageType']?:'')=='CreateFillCommand'")
  @Transactional
  public void createFillCommandReceived(String messageJson) throws JsonParseException, JsonMappingException, IOException {
    Message<CreateFillCommandPayload> message = new ObjectMapper().readValue(messageJson, new TypeReference<Message<CreateFillCommandPayload>>(){});
    CreateFillCommandPayload createFillCommand = message.getPayload();
    createFillCommand.setRefId(message.getId());

    System.out.println("Retrieve payment: " + createFillCommand.getPatientName() + " for " + createFillCommand.getRxNumber());

    
    camunda.getRuntimeService().createMessageCorrelation(message.getMessageType()) //
      .processInstanceBusinessKey(message.getTraceId())
      .setVariable("rxNumber", createFillCommand.getRxNumber()) //
      .setVariable("patientName", createFillCommand.getPatientName()) //
      .setVariable("refId", createFillCommand.getRefId()) //
      .setVariable("correlationId", message.getCorrelationId()) //
      .correlateWithResult();    
  }

}
