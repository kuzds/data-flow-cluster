package ru.kuzds.userflow.camel.web2db.config;

import lombok.RequiredArgsConstructor;
import org.apache.camel.component.cxf.common.DataFormat;
import org.apache.camel.component.cxf.jaxws.CxfEndpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.kuzds.userflow.userservice.UserServicePortType;

import javax.xml.namespace.QName;

@Configuration
@RequiredArgsConstructor
//@ImportResource("classpath:applicationContext.xml")
public class CxfConfig {

    @Bean
    CxfEndpoint userEndpoint() {
        final CxfEndpoint endpoint = new CxfEndpoint();
        endpoint.setServiceNameAsQName(new QName("http://www.kuzds.ru/userflow/userservice", "UserService"));
        endpoint.setServiceClass(UserServicePortType.class);
        endpoint.setDataFormat(DataFormat.PAYLOAD);
        endpoint.setAddress("/UserService");
        return endpoint;
    }
}
