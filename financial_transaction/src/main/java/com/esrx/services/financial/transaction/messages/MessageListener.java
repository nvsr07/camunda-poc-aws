package com.esrx.services.financial.transaction.messages;

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
      condition="(headers['messageType']?:'')=='AuthorizeCreditCardCommand'")
  @Transactional
  public void authorizeCreditCardCommandReceived(String messageJson) throws JsonParseException, JsonMappingException, IOException {
    Message<AuthorizedCreditCardPayload> message = new ObjectMapper().readValue(messageJson, new TypeReference<Message<AuthorizedCreditCardPayload>>(){});
    AuthorizedCreditCardPayload authorizeCreditCardCommand = message.getPayload();
    
    System.out.println("Retrieve payment: " + authorizeCreditCardCommand.getCardHolderName()+ " for " + authorizeCreditCardCommand.getChargeAmount());
    
    camunda.getRuntimeService().createMessageCorrelation(message.getMessageType()) //
      .processInstanceBusinessKey(message.getTraceId())
      .setVariable("amount", authorizeCreditCardCommand.getChargeAmount()) //
      .setVariable("creditCardHolderName", authorizeCreditCardCommand.getCardHolderName()) //
             .setVariable("creditCardNumber", authorizeCreditCardCommand.getCreditCardNo()) //
      .setVariable("refId", authorizeCreditCardCommand.getCcCode()) //
      .setVariable("correlationId", message.getCorrelationId()) //
      .correlateWithResult();    
  }
    
    
}
