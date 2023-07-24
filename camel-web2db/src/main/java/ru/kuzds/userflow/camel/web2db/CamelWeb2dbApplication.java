package ru.kuzds.userflow.camel.web2db;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
//@EnableEurekaClient
@ImportResource("classpath:applicationContext.xml")
public class CamelWeb2dbApplication {

    public static void main(String[] args) {
        SpringApplication.run(CamelWeb2dbApplication.class, args);
    }

}
