package com.esrx.services.checkout;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication( exclude = {
  DataSourceAutoConfiguration.class
})
public class CheckoutApplication {

  public static void main(String[] args) {
    SpringApplication.run(CheckoutApplication.class, args);
  }

}
