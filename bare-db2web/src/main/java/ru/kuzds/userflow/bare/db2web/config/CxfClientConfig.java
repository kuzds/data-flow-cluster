package ru.kuzds.userflow.bare.db2web.config;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.logging.FaultListener;
import org.apache.cxf.message.Message;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.kuzds.userflow.userservice.UserServicePortType;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CxfClientConfig {

    // https://docs.spring.io/spring-cloud-netflix/docs/current/reference/html/#using-the-eurekaclient
//    @Autowired
//    private EurekaClient discoveryClient;
//
//    public String serviceUrl() {
//        InstanceInfo instance = discoveryClient.getNextServerFromEureka("STORES", false);
//        return instance.getHomePageUrl();
//    }

    private final static String address = "http://localhost:3333/cxf/UserService";

    @Bean
    public UserServicePortType userServiceProxy() {
        JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
        jaxWsProxyFactoryBean.setServiceClass(UserServicePortType.class);
        jaxWsProxyFactoryBean.setAddress(address);

        Map<String, Object> properties = new HashMap<>();
        properties.put("org.apache.cxf.logging.FaultListener", new MyFaultListener());
        jaxWsProxyFactoryBean.setProperties(properties);

        return (UserServicePortType) jaxWsProxyFactoryBean.create();
    }

    //https://stackoverflow.com/questions/37984617/propagate-exception-from-cxf-interceptor-to-exception-mapper
    public static class MyFaultListener implements FaultListener {
        public boolean faultOccurred(final Exception exception, final String description, final Message message) {
            //return false to avoid warning of default CXF logging interceptor
            return false;
        }
    }
}

