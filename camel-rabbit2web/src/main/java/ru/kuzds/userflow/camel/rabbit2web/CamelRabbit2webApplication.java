package ru.kuzds.userflow.camel.rabbit2web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class CamelRabbit2webApplication {

	public static void main(String[] args) {
		SpringApplication.run(CamelRabbit2webApplication.class, args);
	}

}
