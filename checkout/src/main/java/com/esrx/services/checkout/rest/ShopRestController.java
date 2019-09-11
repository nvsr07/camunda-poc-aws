package com.esrx.services.checkout.rest;


import com.esrx.services.checkout.domain.Order;
import com.esrx.services.checkout.messages.Message;
import com.esrx.services.checkout.messages.MessageSender;
import com.esrx.services.order.dto.*;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@RestController
public class ShopRestController {
  
  @Autowired
  private MessageSender messageSender;
  
  @RequestMapping(path = "/api/cart/order", method = PUT)
  public String placeOrder(@RequestParam(value = "customerId") String customerId) {

    /*
    
    Order order = new Order();
    order.addItem("article1", 5);
    order.addItem("article2", 10);
    
    order.setCustomer(new Customer("Camunda", "Zossener Strasse 55\n10961 Berlin\nGermany"));*/



    Faker faker = new Faker();

    OrderDto order = new OrderDto();
    order.setOrderId(faker.idNumber().invalid());
    order.setNeedByDate(faker.date().birthday());
   // order.setPatientId(faker.number().digits(6));
    order.setPromisedDate(faker.date().birthday());
    order.setShipByDate(faker.date().birthday());

    PostalAddressDto address = new PostalAddressDto();
    List<String> streetAddress = new ArrayList<String>();
    streetAddress.add(faker.address().buildingNumber());
    streetAddress.add(faker.address().streetAddress());
    streetAddress.add(faker.address().streetName());

    address.setStreetAddress(streetAddress);
    //address.setStreetAddressLine1(faker.address().buildingNumber());
    address.setCity(faker.address().cityName());
    address.setPostalCode(faker.address().zipCode());
    StateDto state = new StateDto();
    state.setCode(faker.address().stateAbbr());
    state.setName(faker.address().state());
    address.setState(state);

    order.setPostalAddress(address);

    StatusDto orderStatus = new StatusDto();
    orderStatus.setValue(faker.lorem().characters(3));
    orderStatus.setEffectiveDateTime(faker.date().birthday());
    orderStatus.setExpirationDateTime(faker.date().birthday());

    StatusDto orderStatus1 = new StatusDto();
    orderStatus.setValue(faker.lorem().characters(3));
    orderStatus.setEffectiveDateTime(faker.date().birthday());
    orderStatus.setExpirationDateTime(faker.date().birthday());

    order.setStatuses(Arrays.asList(orderStatus, orderStatus1));

    ItemDto orderItem = new ItemDto();
    orderItem.setExternalOrderId(faker.number().digits(6));
    orderItem.setLabelPrescriptionNumber(faker.number().digits(6));
    orderItem.setProductTypeCode(faker.lorem().characters(3));
    orderItem.setPackDateTime(faker.date().birthday());
   // orderItem.getPendSourceDateTime(faker.date().birthday());

    StatusDto orderItemStatus = new StatusDto();
    orderItemStatus.setValue(faker.number().digits(6));
    orderItemStatus.setEffectiveDateTime(faker.date().birthday());
    orderItemStatus.setExpirationDateTime(faker.date().birthday());
    //orderItemStatus.setStatusTypeCode(faker.lorem().characters(3));

    StatusDto orderItemStatus1 = new StatusDto();
    orderItemStatus1.setValue(faker.number().digits(6));
    orderItemStatus1.setEffectiveDateTime(faker.date().birthday());
    orderItemStatus1.setExpirationDateTime(faker.date().birthday());
    orderItem.setStatuses(Arrays.asList(orderItemStatus, orderItemStatus1));


    PaymentDto orderItemPayment = new PaymentDto();
    orderItemPayment.setGroupId(faker.number().digits(6));
    AmountDto amountDto = new AmountDto();
    amountDto.setAmount(faker.number().digits(5));
    amountDto.setCurrency("USD");
    orderItemPayment.setOwedAmount(amountDto);
    orderItemPayment.setPatientBillIndicator(faker.bool().bool());
    orderItemPayment.setPlanId(faker.number().digits(6));
    orderItemPayment.setRelativeId(UUID.randomUUID());

    PaymentDto orderItemPayment1 = new PaymentDto();
    orderItemPayment1.setGroupId(faker.number().digits(6));
    AmountDto amountDto1 = new AmountDto();
    amountDto1.setAmount(faker.number().digits(5));
    amountDto1.setCurrency("USD");
    orderItemPayment1.setOwedAmount(amountDto1);
    orderItemPayment1.setPatientBillIndicator(faker.bool().bool());
    orderItemPayment1.setPlanId(faker.number().digits(6));
    orderItemPayment1.setRelativeId(UUID.randomUUID());

    orderItem.setPayments(Arrays.asList(orderItemPayment, orderItemPayment1));

    order.setItems(Arrays.asList(orderItem));
    
    Message<OrderDto> message = new Message<OrderDto>("OrderCreatedEvent", order);
    message.setCorrelationId(UUID.randomUUID().toString());
    messageSender.send(message);
        
    // note that we cannot easily return an order id here - as everything is asynchronous
    // and blocking the client is not what we want.
    // but we return an own correlationId which can be used in the UI to show status maybe later
    return "{\"traceId\": \"" + message.getTraceId() + "\"}";
  }

}