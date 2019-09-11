package com.esrx.services.dur;

import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableProcessApplication
public class DurServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DurServiceApplication.class, args);
	}

}
