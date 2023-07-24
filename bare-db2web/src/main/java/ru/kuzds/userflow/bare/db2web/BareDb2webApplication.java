package ru.kuzds.userflow.bare.db2web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
//@EnableEurekaClient
@EnableScheduling
@EnableFeignClients
public class BareDb2webApplication {

	public static void main(String[] args) {
		SpringApplication.run(BareDb2webApplication.class, args);
	}
}
