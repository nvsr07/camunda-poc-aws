package com.esrx.services.financial.transaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;

@SpringBootApplication
@EnableProcessApplication
public class FinancialTransactionApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinancialTransactionApplication.class, args);
	}

}
