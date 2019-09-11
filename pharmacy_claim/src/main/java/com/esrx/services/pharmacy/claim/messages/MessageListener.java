package com.esrx.services.pharmacy.claim.messages;

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
      condition="(headers['messageType']?:'')=='AdjudicateClaimCommand'")
  @Transactional
  public void adjudicateClaimCommandReceived(String messageJson) throws JsonParseException, JsonMappingException, IOException {
    Message<AdjudicateClaimCommandPayload> message = new ObjectMapper().readValue(messageJson, new TypeReference<Message<AdjudicateClaimCommandPayload>>(){});
    AdjudicateClaimCommandPayload adjudicateClaimCommand = message.getPayload();
    
    System.out.println("Retrieve payment: " + adjudicateClaimCommand.getClaimCenterName() + " for " + adjudicateClaimCommand.getPharmacyId());
    
    camunda.getRuntimeService().createMessageCorrelation(message.getMessageType()) //
      .processInstanceBusinessKey(message.getTraceId())
      .setVariable("claimCenterNo", adjudicateClaimCommand.getClaimCenterNo()) //
      .setVariable("claimCenterName", adjudicateClaimCommand.getClaimCenterName()) //
            .setVariable("pharmacyId", adjudicateClaimCommand.getPharmacyId()) //
      .setVariable("refId", adjudicateClaimCommand.getRefId()) //
      .setVariable("correlationId", message.getCorrelationId()) //
      .correlateWithResult();    
  }
}
