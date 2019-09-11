package com.esrx.services.fulfillment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;

@SpringBootApplication
@EnableProcessApplication
public class FulfillmentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FulfillmentServiceApplication.class, args);
	}

}
