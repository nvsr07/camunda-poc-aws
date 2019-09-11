package com.esrx.services.order.flow;


import com.esrx.services.order.domain.Order;
import com.esrx.services.order.domain.OrderItem;
import com.esrx.services.order.dto.ItemDto;
import com.esrx.services.order.dto.StatusDto;
import com.esrx.services.order.messages.Message;
import com.esrx.services.order.messages.MessageSender;

import com.github.javafaker.Faker;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AddOrderLinesAdapter implements JavaDelegate {
  
  @Autowired
  private MessageSender messageSender;


  @Override
  public void execute(DelegateExecution context) throws Exception {
    Order order = null;
    String traceId = context.getProcessBusinessKey();

    String orderId = (String)context.getVariable("orderId");
    String correlationId = (String)context.getVariable("correlationId");

    Faker faker = new Faker();

    List<ItemDto> orderItems = new ArrayList<>();
    ItemDto orderItem = new ItemDto();
    orderItem.setLabelPrescriptionNumber("RX01234567");
    orderItem.setPackDateTime(faker.date().birthday());
    orderItem.setProductTypeCode(faker.lorem().characters(3));
    List<StatusDto>  status = new ArrayList<StatusDto>();
    StatusDto statusDto = new StatusDto();
    statusDto.setValue(faker.lorem().characters(4));
    status.add(statusDto);
    orderItem.setStatuses(status);

    orderItems.add(orderItem);

    List<ItemDto> orderItems1 = new ArrayList<>();
    ItemDto orderItem1 = new ItemDto();
    orderItem1.setLabelPrescriptionNumber("RX01334569");
    orderItem1.setPackDateTime(faker.date().birthday());
    orderItem1.setProductTypeCode(faker.lorem().characters(3));
    List<StatusDto>  status1 = new ArrayList<StatusDto>();
    StatusDto statusDto1 = new StatusDto();
    statusDto1.setValue(faker.lorem().characters(4));
    status1.add(statusDto);
    orderItem1.setStatuses(status);

    orderItems.add(orderItem1);


    messageSender.send( //
        new Message<Object>( //
            "CreateFillCommand", //
            traceId, //
            new CreateFillCommandPayload() //
                    .setRefId(orderId) //
                    .setLineItems(orderItems)//
                    .setPatientName(faker.name().fullName())//
                    .setRxNumber(faker.number().digits(8))).setCorrelationId(correlationId));

  }


}
