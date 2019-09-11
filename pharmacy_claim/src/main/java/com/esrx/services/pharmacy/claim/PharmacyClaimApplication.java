package com.esrx.services.pharmacy.claim;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;

@SpringBootApplication
@EnableProcessApplication
public class PharmacyClaimApplication {

	public static void main(String[] args) {
		SpringApplication.run(PharmacyClaimApplication.class, args);
	}

}
