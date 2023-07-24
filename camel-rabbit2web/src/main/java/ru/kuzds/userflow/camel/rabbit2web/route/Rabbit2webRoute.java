package ru.kuzds.userflow.camel.rabbit2web.route;

import lombok.RequiredArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;
import ru.kuzds.userflow.camel.rabbit2web.mapper.UserMapper;
import ru.kuzds.userflow.userservice.SaveUserResponse;
import ru.kuzds.userflow.userservice.User;

@Component
@RequiredArgsConstructor
public class Rabbit2webRoute extends RouteBuilder {

    public final static String EXCHANGE_NAME = "user.flow.cluster.exchange";
    public final static String ROUTING_KEY = "user.flow.cluster.queue";
    public final static String RABBITMQ = String.format(
        "spring-rabbitmq:%s?" +
            "routingKey=%s&" +
            "arg.queue.autoDelete=true", EXCHANGE_NAME, ROUTING_KEY
    ); // ?autoDeclare=true


    public static final String DR_TO_REST = "direct:to-rest";
    public static final String DR_TO_SOAP = "direct:to-soap";
    public static final String REST_URL = "http://localhost:5555/rest/user";

    public final UserMapper userMapper;

    @Override
    public void configure() {
        from(RABBITMQ)
            .unmarshal(new JacksonDataFormat(User.class))
            .choice()
                .when(simple("${body.transferType} == 'REST'"))
                    .to(DR_TO_REST)
                .when(simple("${body.transferType} == 'SOAP'"))
                    .to(DR_TO_SOAP)
                .otherwise()
                    .log("User discarded due to incorrect transferType. Use 'REST' or 'SOAP' values")
            .end() //choice
        ;

        from(DR_TO_REST).routeId("to-rest")
            .bean(userMapper, "toSaveUserRequest")
            .marshal().json(JsonLibrary.Jackson, true)
            .removeHeader("*")
            .setHeader(Exchange.HTTP_METHOD, constant("POST"))
            .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
            .to(REST_URL)
//            .unmarshal().json(JsonLibrary.Jackson, true)
            .unmarshal(new JacksonDataFormat(SaveUserResponse.class))
            .log("User sent using ${body.user.transferType}")
        ;

        from(DR_TO_SOAP).routeId("to-soap")
            .bean(userMapper, "toSaveUserRequest")
            .log("Sending using ${body.user.transferType} body=${body}")
        ;
    }
}