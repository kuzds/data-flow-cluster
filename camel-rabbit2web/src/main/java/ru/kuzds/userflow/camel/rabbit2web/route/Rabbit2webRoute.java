package ru.kuzds.userflow.camel.rabbit2web.route;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import lombok.RequiredArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.cxf.common.message.CxfConstants;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.kuzds.userflow.camel.rabbit2web.mapper.UserMapper;
import ru.kuzds.userflow.userservice.SaveUserRequest;
import ru.kuzds.userflow.userservice.SaveUserResponse;
import ru.kuzds.userflow.userservice.User;
import ru.kuzds.userflow.userservice.UserServicePortType;

@Component
@RequiredArgsConstructor
public class Rabbit2webRoute extends RouteBuilder {

    public static final String DR_TO_REST = "direct:to-rest";
    public static final String DR_TO_SOAP = "direct:to-soap";
    public static final String DR_LOG = "direct:log-user";

    private final UserMapper userMapper;
    private final EurekaClient eurekaClient;

    @Value("${userflow.target-service-name}")
    private String targetServiceName;

    @Value("${userflow.rabbitmq.exchange}")
    private String rabbitmqExchange;

    @Value("${userflow.rabbitmq.routing-key}")
    private String rabbitmqRoutingKey;

    @Override
    public void configure() {

        String rabbitmq = String.format(
            "spring-rabbitmq:%s?" +
                "routingKey=%s&" +
                "arg.queue.autoDelete=true", rabbitmqExchange, rabbitmqRoutingKey
        ); // ?autoDeclare=true

        InstanceInfo instance = eurekaClient.getNextServerFromEureka(targetServiceName, false);

        String restUrl = instance.getHomePageUrl() + "rest/user";
        String cxfUrl = instance.getHomePageUrl() + "cxf/UserService";

        from(rabbitmq).routeId("from-rabbitmq")
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
            .wireTap(DR_LOG)
            .marshal().json(JsonLibrary.Jackson, true)
            .removeHeader("*")
            .setHeader(Exchange.HTTP_METHOD, constant("POST"))
            .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
            .to(restUrl)
            .unmarshal(new JacksonDataFormat(SaveUserResponse.class))
        ;

        from(DR_TO_SOAP).routeId("to-soap")
            .bean(userMapper, "toSaveUserRequest")
            .wireTap(DR_LOG)
            .removeHeaders("*")
            .setHeader(CxfConstants.OPERATION_NAME, constant("SaveUser"))
            .toF("cxf://%s?serviceClass=%s", cxfUrl, UserServicePortType.class.getName())
            .convertBodyTo(SaveUserResponse.class)
        ;

        from(DR_LOG).routeId("log-user")
            .validate(body().isInstanceOf(SaveUserRequest.class))
            .bean(userMapper, "toString")
            .log("Sending ${body}")
        ;
    }
}