package ru.kuzds.userflow.bare.web2rabbit.config;

import lombok.RequiredArgsConstructor;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.kuzds.userflow.bare.web2rabbit.endpoint.EmployeeService;
import ru.kuzds.userflow.bare.web2rabbit.endpoint.UserEndpoint;

import javax.xml.ws.Endpoint;

// https://cxf.apache.org/docs/springboot.html#SpringBoot-SpringBootCXFJAX-WSStarter
@Configuration
@RequiredArgsConstructor
public class CxfEndpointConfig {

    private final Bus bus;

    @Bean
    public Endpoint publishUserEndpoint(UserEndpoint userEndpoint) {
        EndpointImpl endpoint = new EndpointImpl(bus, userEndpoint);
        endpoint.publish("/UserService");
        return endpoint;
    }

    @Bean
    public Endpoint publishEmployeeEndpoint(EmployeeService employeeService) {
        EndpointImpl endpoint = new EndpointImpl(bus, employeeService);
        endpoint.publish("/EmployeeService");
        return endpoint;
    }
}
