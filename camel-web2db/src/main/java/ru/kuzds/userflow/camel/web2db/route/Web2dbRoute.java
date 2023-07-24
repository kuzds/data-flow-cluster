package ru.kuzds.userflow.camel.web2db.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.kuzds.userflow.userservice.SaveUserRequest;
import ru.kuzds.userflow.userservice.SaveUserResponse;

@Component
public class Web2dbRoute extends RouteBuilder {


    @Value("${camel.rest.context-path}")
    private String contextPath;

    @Override
    public void configure() {

        restConfiguration()
            .component("servlet")
            .bindingMode(RestBindingMode.json)
            .dataFormatProperty("prettyPrint", "true")
            .enableCORS(true)
            .contextPath(contextPath)
        ;

        rest("/user")
            .post().type(SaveUserRequest.class).outType(SaveUserResponse.class)
            .to("direct:save-user")
        ;

        from("cxf:bean:userEndpoint")
            .log("SOAP body=${body}")
        ;

        from("direct:save-user")
            .log("body: ${body}")
            .to("elsql:insertUser:sql/user.elsql")
        ;
    }
}
