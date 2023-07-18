package ru.kuzds.userflow.camel.rabbit2web.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class Rabbit2WebRoute extends RouteBuilder {

    public final static String EXCHANGE_NAME = "user.flow.cluster.exchange";
    public final static String ROUTING_KEY = "user.flow.cluster.queue";
    public final static String RABBITMQ = String.format(
        "spring-rabbitmq:%s?" +
        "routingKey=%s&" +
        "arg.queue.autoDelete=true", EXCHANGE_NAME, ROUTING_KEY
    ); // ?autoDeclare=true

    @Override
    public void configure() {
        from(RABBITMQ)
            .log("body: ${body}")
        ;
    }
}