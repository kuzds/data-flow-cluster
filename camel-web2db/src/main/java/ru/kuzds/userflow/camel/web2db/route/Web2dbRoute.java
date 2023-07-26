package ru.kuzds.userflow.camel.web2db.route;

import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.kuzds.userflow.camel.web2db.mapper.UserMapper;
import ru.kuzds.userflow.userservice.SaveUserRequest;
import ru.kuzds.userflow.userservice.SaveUserResponse;

import javax.xml.bind.JAXBException;

@Component
@RequiredArgsConstructor
public class Web2dbRoute extends RouteBuilder {

    public static final String DR_SAVE_TO_DB = "direct:save-user-to-db";
    public static final String DR_FROM_REST = "direct:from-rest";

    public final UserMapper userMapper;

    @Value("${camel.rest.context-path}")
    private String contextPath;

    @Override
    public void configure() {

        // XML Data Format
//        JAXBContext con = JAXBContext.newInstance(SaveUserRequest.class);
//        JaxbDataFormat xmlDataFormat = new JaxbDataFormat(con);

//        // JSON Data Format
//        JacksonDataFormat jsonDataFormat = new JacksonDataFormat(SaveUserResponse.class);

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

        from(DR_FROM_REST)
            .wireTap(DR_SAVE_TO_DB)
            .bean(userMapper, "toSaveUserResponse")
        ;

        from("cxf:bean:userEndpoint")
            .convertBodyTo(SaveUserRequest.class)
            .wireTap(DR_SAVE_TO_DB)
            .bean(userMapper, "toSaveUserResponse")
        ;

        from(DR_SAVE_TO_DB)
            .log("Saving to database body=${body}")
//            .process(exchange -> {
//                exchange.getIn().getBody();
//            })
            .to("elsql:insertUser:sql/user.elsql")
        ;
    }
}
