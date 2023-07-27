package ru.kuzds.userflow.bare.web2rabbit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class BareWeb2rabbitApplication {

	public static void main(String[] args) {
		SpringApplication.run(BareWeb2rabbitApplication.class, args);
	}
}
