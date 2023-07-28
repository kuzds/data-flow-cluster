package ru.kuzds.userflow.camel.web2db.route;

import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.kuzds.userflow.camel.web2db.mapper.UserMapper;
import ru.kuzds.userflow.userservice.SaveUserRequest;
import ru.kuzds.userflow.userservice.SaveUserResponse;

@Component
@RequiredArgsConstructor
public class Web2dbRoute extends RouteBuilder {

    public static final String DR_SAVE_TO_DB = "direct:save-user-to-db";
    public static final String DR_FROM_REST = "direct:from-rest";

    public static final String DR_LOG = "direct:log-user";

    public final UserMapper userMapper;

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
            .to(DR_FROM_REST)
        ;

        from(DR_FROM_REST).routeId("from-rest")
            .wireTap(DR_SAVE_TO_DB)
            .bean(userMapper, "toSaveUserResponse")
        ;

        from("cxf:bean:userEndpoint").routeId("from-cxf")
            .convertBodyTo(SaveUserRequest.class)
            .wireTap(DR_SAVE_TO_DB)
            .bean(userMapper, "toSaveUserResponse")
        ;

        from(DR_SAVE_TO_DB).routeId("save-user-to-db")
            .wireTap(DR_LOG)
            .to("elsql:insertUser:sql/user.elsql")
        ;

        from(DR_LOG).routeId("log-user")
            .validate(body().isInstanceOf(SaveUserRequest.class))
            .bean(userMapper, "toString")
            .log("Saving to database ${body}")
        ;
    }
}
