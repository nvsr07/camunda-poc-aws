package com.esrx.services.order;


import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.camunda.bpm.spring.boot.starter.event.PostDeployEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.event.EventListener;

@SpringBootApplication(
		scanBasePackages = {"com.esrx.services", "com.esrx.services.order", "com.esrx.services.order.controller"},
		exclude = {
                MongoAutoConfiguration.class,
                MongoDataAutoConfiguration.class
               // MongoRepositoriesAutoConfiguration.class

		}
)
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableCaching
@EnableProcessApplication

public class OrderApplication {
	@Autowired
	private RuntimeService runtimeService;

    public static void main(String[] args) {
		SpringApplication.run(OrderApplication.class, args);
	}


	@EventListener
    private void processPostDeploy(PostDeployEvent event) {
        //runtimeService.startProcessInstanceByKey("order");
        runtimeService.startProcessInstanceByKey("order_api_process");

    }


}