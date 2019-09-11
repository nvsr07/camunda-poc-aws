package com.esrx.services.membership.messages;

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
      condition="(headers['messageType']?:'')=='AttachMembershipCommand'")
  @Transactional
  public void retrievePaymentCommandReceived(String messageJson) throws JsonParseException, JsonMappingException, IOException {
    Message<AttachMembershipCommandPayload> message = new ObjectMapper().readValue(messageJson, new TypeReference<Message<AttachMembershipCommandPayload>>(){});
    AttachMembershipCommandPayload createShipmentCommand = message.getPayload();
    
    System.out.println("Retrieve payment: " + createShipmentCommand.getRefId() + " for " + createShipmentCommand.getRefId());
    
    camunda.getRuntimeService().createMessageCorrelation(message.getMessageType()) //
      .processInstanceBusinessKey(message.getTraceId())
      .setVariable("patientName", createShipmentCommand.getPatientName()) //
      .setVariable("patinetNumber", createShipmentCommand.getPatientNumber()) //
      .setVariable("refId", createShipmentCommand.getRefId()) //
      .setVariable("correlationId", message.getCorrelationId()) //
      .correlateWithResult();    
  }
    
    
}
