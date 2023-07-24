package ru.kuzds.userflow.camel.web2db.config;

import lombok.RequiredArgsConstructor;
import org.apache.camel.component.cxf.common.DataFormat;
import org.apache.camel.component.cxf.jaxws.CxfEndpoint;
import org.apache.cxf.Bus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.kuzds.userflow.userservice.UserServicePortType;

@Configuration
@RequiredArgsConstructor
public class CxfConfig {

//    private final Bus bus;
//
//    @Bean
//    CxfEndpoint userEndpoint() {
//
//
//        final CxfEndpoint endpoint = new CxfEndpoint();
//        endpoint.setBus(bus);
//        endpoint.setServiceClass(UserServicePortType.class);
////        endpoint.setEndpointName("UserServicePortType");
//        endpoint.setDataFormat(DataFormat.PAYLOAD);
//        endpoint.setServiceName("UserService");
//        endpoint.setAddress("/UserService"); //http://localhost:9000/myservice //http://localhost:5555
////        endpoint.setAddress("/cxf/UserService");
////        endpoint.setDefaultOperationNamespace("http://www.kuzds.ru/userflow/userservice");
//        return endpoint;
//    }
}
