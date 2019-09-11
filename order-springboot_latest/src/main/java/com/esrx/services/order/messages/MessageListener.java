package com.esrx.services.order.messages;

import com.esrx.services.order.dto.OrderDto;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
@EnableBinding(Sink.class)
public class MessageListener {


    @Autowired
    private ProcessEngine camunda;

    @Autowired
    private MessageSender messageSender;
    
  /**
   * Handles incoming OrderPlacedEvents. 
   * 
   *  Using the conditional {@link StreamListener} from 
   * https://github.com/spring-cloud/spring-cloud-stream/blob/master/spring-cloud-stream-core-docs/src/main/asciidoc/spring-cloud-stream-overview.adoc
   * in a way close to what Axion
   *  would do (see e.g. https://dturanski.wordpress.com/2017/03/26/spring-cloud-stream-for-event-driven-architectures/)
   */
  @StreamListener(target = Sink.INPUT, 
      condition="(headers['messageType']?:'')=='OrderCreatedEvent'")
  @Transactional
  public void orderPlacedEventReceived(Message<OrderDto> message) throws JsonParseException, JsonMappingException, IOException {
      OrderDto order = message.getPayload();


      Map<String, String> header = new HashMap<>();

      System.out.println("New order placed, start flow. " + message);

      // persist domain entity
      //repository.save(order);
      Faker faker = new Faker();
      // and kick of a new flow instance
      ObjectValue orderDataValue = Variables.objectValue(order)
              .serializationDataFormat(Variables.SerializationDataFormats.JAVA)
              .create();

      camunda.getRuntimeService().createMessageCorrelation(message.getMessageType())
      .processInstanceBusinessKey(message.getTraceId())
      //.setVariable("orderId", order.getOrderId())//
              .setVariable("orderId", faker.idNumber().valid())//
              .setVariable("correlationId", message.getCorrelationId())//
              .setVariable("refId", UUID.randomUUID().toString())//
      .correlateWithResult();

      /*messageSender.send( //
              new Message<>( //
                      "CreateFillCommand", //
                      message.getTraceId(), //
                      new CreateFillCommandPayload() //
                              .setRxNumber("123456") //
                              .setPatientName("Venkat Namala")));*/
  }

    /*@StreamListener(target = Sink.INPUT,
            condition="(headers['messageType']?:'')=='OrderCreatedEvent'")
    @Transactional

    public void orderCreateEventListner(String messageJson) throws Exception {
        Message<JsonNode> message = new ObjectMapper().readValue( //
                messageJson, //
                new TypeReference<Message<JsonNode>>() {});

        long correlatingInstances = camunda.getRuntimeService().createExecutionQuery() //
                .messageEventSubscriptionName(message.getMessageType()) //
                .processInstanceBusinessKey(message.getTraceId()) //
                .count();
        if (correlatingInstances==1) {
            System.out.pintln("Correlating " + message + " to waiting flow instance");

            camunda.getRuntimeService().createMessageCorrelation(message.getMessageType())
                    .processInstanceBusinessKey(message.getTraceId())
                    .setVariableLocal(//
                            "PAYLOAD_" + message.getMessageType(), //
                            SpinValues.jsonValue(message.getPayload().toString()).create())//
                    .correlateWithResult();

            messageSender.send( //
                    new Message<>( //
                            "CreateFillCommand", //
                            message.getTraceId(), //
                            new CreateFillCommandPayload() //
                                    .setRxNumber("123456") //
                                    .setPatientName("Venkat Namala")));
        }

    }


    @StreamListener(target = Sink.INPUT,
            condition="(headers['messageType']?:'')=='CreateFillCompleteEvent'")
    @Transactional
    public void createFillCompleteEventListner(String messageJson) throws Exception {
        System.out.println("########## Inside CreateFillCompleteEvent Listner Method ######### : "  + messageJson);
        Message<JsonNode> message = new ObjectMapper().readValue( //
                messageJson, //
                new TypeReference<Message<JsonNode>>() {});

        long correlatingInstances = camunda.getRuntimeService().createExecutionQuery() //
                .messageEventSubscriptionName(message.getMessageType()) //
                .processInstanceBusinessKey(message.getTraceId()) //
                .count();

        System.out.println("########## Inside CreateFillCompleteEvent Listner Method 1 ######### : "  + correlatingInstances);
        if (correlatingInstances==1) {
            System.out.println("Correlating " + message + " to waiting flow instance");

            camunda.getRuntimeService().createMessageCorrelation(message.getMessageType())
                    .processInstanceBusinessKey(message.getTraceId())
                    .setVariable(//
                            "PAYLOAD_" + message.getMessageType(), //
                            SpinValues.jsonValue(message.getPayload().toString()).create())//
                    .correlateWithResult();


        }

        Faker faker = new Faker();
        List<String> itemNos = new ArrayList<String>();
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

        messageSender.send( //
                new Message<Object>( //
                        "ReserveInventoryCommand", //
                        message.getTraceId(), //
                        new ReserveInventoryPayload() //
                                .setItemNos(itemNos)//
                                .setOrderPlacedDate(orderPlacedDates)//
                                .setQuantity(quantity)//
                                .setRefId(faker.idNumber().valid())));
        System.out.println("########## Inside CreateFillCompleteEvent Listner Method 2 ######### : ReserveInventoryCommand " );

    }

    @StreamListener(target = Sink.INPUT,
            condition="(headers['messageType']?:'')=='InventoryReservedEvent'")
    @Transactional
    public void inventoryReservedEventListner(String messageJson) throws Exception {
        Message<JsonNode> message = new ObjectMapper().readValue( //
                messageJson, //
                new TypeReference<Message<JsonNode>>() {});

        long correlatingInstances = camunda.getRuntimeService().createExecutionQuery() //
                .messageEventSubscriptionName(message.getMessageType()) //
                .processInstanceBusinessKey(message.getTraceId()) //
                .count();

        if (correlatingInstances==1) {
            System.out.println("Correlating " + message + " to waiting flow instance");

            camunda.getRuntimeService().createMessageCorrelation(message.getMessageType())
                    .processInstanceBusinessKey(message.getTraceId())
                    .setVariable(//
                            "PAYLOAD_" + message.getMessageType(), //
                            SpinValues.jsonValue(message.getPayload().toString()).create())//
                    .correlateWithResult();


        }

        Faker faker = new Faker();
        messageSender.send( //
                new Message<>( //
                        "AttachMembershipCommand", //
                        message.getTraceId(), //
                        new AttachMembershipCommandPayload() //
                                .setRefId(faker.idNumber().valid()) //
                                .setPatientName(faker.name().fullName())//
                                .setPatientNumber(faker.number().digits(6))));

    }

    @StreamListener(target = Sink.INPUT,
            condition="(headers['messageType']?:'')=='MembershipAttachedEvent'")
    @Transactional
    public void attachMembershipEventListner(String messageJson) throws Exception {
        Message<JsonNode> message = new ObjectMapper().readValue( //
                messageJson, //
                new TypeReference<Message<JsonNode>>() {});

        long correlatingInstances = camunda.getRuntimeService().createExecutionQuery() //
                .messageEventSubscriptionName(message.getMessageType()) //
                .processInstanceBusinessKey(message.getTraceId()) //
                .count();

        if (correlatingInstances==1) {
            System.out.println("Correlating " + message + " to waiting flow instance");

            camunda.getRuntimeService().createMessageCorrelation(message.getMessageType())
                    .processInstanceBusinessKey(message.getTraceId())
                    .setVariable(//
                            "PAYLOAD_" + message.getMessageType(), //
                            SpinValues.jsonValue(message.getPayload().toString()).create())//
                    .correlateWithResult();


        }
        Faker faker = new Faker();
        messageSender.send( //
                new Message<>( //
                        "AdjudicateClaimCommand", //
                        message.getTraceId(), //
                        new AdjuducateClaimPayload() //
                                .setProviderNumber(faker.name().fullName())//
                                .setPharmacyId(faker.idNumber().valid())//
                                .setNpi(faker.idNumber().ssnValid())//
                                .setClaimCenterNo(faker.number().digits(4))//
                                .setClaimCenterName(faker.name().name())));


    }

    @StreamListener(target = Sink.INPUT,
            condition="(headers['messageType']?:'')=='ClaimAdjuducatedEvent'")
    @Transactional
    public void claimAdjuducatedEventListner(String messageJson) throws Exception {
        Message<JsonNode> message = new ObjectMapper().readValue( //
                messageJson, //
                new TypeReference<Message<JsonNode>>() {});

        long correlatingInstances = camunda.getRuntimeService().createExecutionQuery() //
                .messageEventSubscriptionName(message.getMessageType()) //
                .processInstanceBusinessKey(message.getTraceId()) //
                .count();

        if (correlatingInstances==1) {
            System.out.println("Correlating " + message + " to waiting flow instance");

            camunda.getRuntimeService().createMessageCorrelation(message.getMessageType())
                    .processInstanceBusinessKey(message.getTraceId())
                    .setVariable(//
                            "PAYLOAD_" + message.getMessageType(), //
                            SpinValues.jsonValue(message.getPayload().toString()).create())//
                    .correlateWithResult();
            }

        messageSender.send( //
                new Message<>( //
                        "PersonalFinancialAccountChekedEvent", //
                        message.getTraceId(), //
                        new RunDURCommandPayload() //
                                .setPickId("123456") //
                                .setLogisticsProvider("Venkat Namala")));

    }

    @StreamListener(target = Sink.INPUT,
            condition="(headers['messageType']?:'')=='RunDURFinishedEvent'")
    @Transactional
    public void runDURFinishedEventListner(String messageJson) throws Exception {
        Message<JsonNode> message = new ObjectMapper().readValue( //
                messageJson, //
                new TypeReference<Message<JsonNode>>() {});

        long correlatingInstances = camunda.getRuntimeService().createExecutionQuery() //
                .messageEventSubscriptionName(message.getMessageType()) //
                .processInstanceBusinessKey(message.getTraceId()) //
                .count();

        if (correlatingInstances==1) {
            System.out.println("Correlating " + message + " to waiting flow instance");

            camunda.getRuntimeService().createMessageCorrelation(message.getMessageType())
                    .processInstanceBusinessKey(message.getTraceId())
                    .setVariable(//
                            "PAYLOAD_" + message.getMessageType(), //
                            SpinValues.jsonValue(message.getPayload().toString()).create())//
                    .correlateWithResult();


        }
        messageSender.send( //
                new Message<>( //
                        "PersonalFinancialAccountChekedEvent", //
                        message.getTraceId(), //
                        new RunDURCommandPayload() //
                                .setPickId("123456") //
                                .setLogisticsProvider("Venkat Namala")));

    }

    @StreamListener(target = Sink.INPUT,
            condition="(headers['messageType']?:'')=='PersonalFinancialAccountChekedEvent'")
    @Transactional
    public void personalFinancialAccountChekedEventListner(String messageJson) throws Exception {
        Message<JsonNode> message = new ObjectMapper().readValue( //
                messageJson, //
                new TypeReference<Message<JsonNode>>() {});

        long correlatingInstances = camunda.getRuntimeService().createExecutionQuery() //
                .messageEventSubscriptionName(message.getMessageType()) //
                .processInstanceBusinessKey(message.getTraceId()) //
                .count();

        if (correlatingInstances==1) {
            System.out.println("Correlating " + message + " to waiting flow instance");

            camunda.getRuntimeService().createMessageCorrelation(message.getMessageType())
                    .processInstanceBusinessKey(message.getTraceId())
                    .setVariable(//
                            "PAYLOAD_" + message.getMessageType(), //
                            SpinValues.jsonValue(message.getPayload().toString()).create())//
                    .correlateWithResult();


        }

        messageSender.send( //
                new Message<>( //
                        "AuthorizeCreditCardCommand", //
                        message.getTraceId(), //
                        new RunDURCommandPayload() //
                                .setPickId("12345678") //
                                .setLogisticsProvider("Venkat Namala")));

    }

    @StreamListener(target = Sink.INPUT,
            condition="(headers['messageType']?:'')=='CreditCardAuthorizedEvent'")
    @Transactional
    public void creditCardAuthorizedEventListner(String messageJson) throws Exception {
        Message<JsonNode> message = new ObjectMapper().readValue( //
                messageJson, //
                new TypeReference<Message<JsonNode>>() {});

        long correlatingInstances = camunda.getRuntimeService().createExecutionQuery() //
                .messageEventSubscriptionName(message.getMessageType()) //
                .processInstanceBusinessKey(message.getTraceId()) //
                .count();

        if (correlatingInstances==1) {
            System.out.println("Correlating " + message + " to waiting flow instance");

            camunda.getRuntimeService().createMessageCorrelation(message.getMessageType())
                    .processInstanceBusinessKey(message.getTraceId())
                    .setVariable(//
                            "PAYLOAD_" + message.getMessageType(), //
                            SpinValues.jsonValue(message.getPayload().toString()).create())//
                    .correlateWithResult();


        }

        messageSender.send( //
                new Message<>( //
                        "FulfillOrderCommand", //
                        message.getTraceId(), //
                        new RunDURCommandPayload() //
                                .setPickId("1234567") //
                                .setLogisticsProvider(" Venkat Namala")));

    }



    @StreamListener(target = Sink.INPUT,
            condition="(headers['messageType']?:'')=='OrderFulfilledEvent'")
    @Transactional
    public void orderFulfilledEventListner(String messageJson) throws Exception {
        Message<JsonNode> message = new ObjectMapper().readValue( //
                messageJson, //
                new TypeReference<Message<JsonNode>>() {});

        long correlatingInstances = camunda.getRuntimeService().createExecutionQuery() //
                .messageEventSubscriptionName(message.getMessageType()) //
                .processInstanceBusinessKey(message.getTraceId()) //
                .count();

        if (correlatingInstances==1) {
            System.out.println("Correlating " + message + " to waiting flow instance");

            camunda.getRuntimeService().createMessageCorrelation(message.getMessageType())
                    .processInstanceBusinessKey(message.getTraceId())
                    .setVariable(//
                            "PAYLOAD_" + message.getMessageType(), //
                            SpinValues.jsonValue(message.getPayload().toString()).create())//
                    .correlateWithResult();


        }
        messageSender.send( //
                new Message<>( //
                        "CreateShipmentCommand", //
                        message.getTraceId(), //
                        new RunDURCommandPayload() //
                                .setPickId("1234567") //
                                .setLogisticsProvider("Venkat Namala")));

    }
    @StreamListener(target = Sink.INPUT,
            condition="(headers['messageType']?:'')=='OrderShippedEvent'")
    @Transactional
    public void orderShippedEventListner(String messageJson) throws Exception {
        Message<JsonNode> message = new ObjectMapper().readValue( //
                messageJson, //
                new TypeReference<Message<JsonNode>>() {});

        long correlatingInstances = camunda.getRuntimeService().createExecutionQuery() //
                .messageEventSubscriptionName(message.getMessageType()) //
                .processInstanceBusinessKey(message.getTraceId()) //
                .count();

        if (correlatingInstances==1) {
            System.out.println("Correlating " + message + " to waiting flow instance");

            camunda.getRuntimeService().createMessageCorrelation(message.getMessageType())
                    .processInstanceBusinessKey(message.getTraceId())
                    .setVariable(//
                            "PAYLOAD_" + message.getMessageType(), //
                            SpinValues.jsonValue(message.getPayload().toString()).create())//
                    .correlateWithResult();


        }

        messageSender.send( //
                new Message<>( //
                        "OrderClosedEvent", //
                        message.getTraceId(), //
                        new RunDURCommandPayload() //
                                .setPickId("1234567") //
                                .setLogisticsProvider("Venkat Namala")));
    }

  *//**
   * Very generic listener for simplicity. It takes all events and checks, if a 
   * flow instance is interested. If yes, they are correlated, 
   * otherwise they are just discarded.
   *  
   * It might make more sense to handle each and every message type individually.
   **/
 @StreamListener(target = Sink.INPUT,
      condition="(headers['messageType']?:'').endsWith('Event')")
  @Transactional
  public void messageReceived(String messageJson) throws Exception {
    Message<JsonNode> message = new ObjectMapper().readValue( //
        messageJson, //
        new TypeReference<Message<JsonNode>>() {});
    
    long correlatingInstances = camunda.getRuntimeService().createExecutionQuery() //
      .messageEventSubscriptionName(message.getMessageType()) //
      .processInstanceBusinessKey(message.getTraceId()) //
      .count();
    
    if (correlatingInstances==1) {
      System.out.println("Correlating " + message + " to waiting flow instance");
     // TypedValue typedTransientObjectValue = Variables.objectValue(message.getPayload(),
       //       true).serializationDataFormat(Variables.SerializationDataFormats.JAVA).create();
      Map<String, Object> varibles = Variables.putValue("PAYLOAD_" + message.getMessageType(), message.getPayload().toString());
        camunda.getRuntimeService().createMessageCorrelation(message.getMessageType())
        .processInstanceBusinessKey(message.getTraceId())
                .setVariables(varibles)
        //.setVariable(//
         //   "PAYLOAD_" + message.getMessageType(), //
         //       typedTransientObjectValue.getValue())//
            //SpinValues.jsonValue(message.getPayload().toString()).create())//
        .correlateWithResult();
    } 
    
  }

}
