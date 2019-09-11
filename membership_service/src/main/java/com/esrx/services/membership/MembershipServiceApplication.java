package com.esrx.services.membership;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;

@SpringBootApplication
@EnableProcessApplication
public class MembershipServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MembershipServiceApplication.class, args);
	}

}
