package ru.kuzds.userflow.bare.db2web.config;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.logging.FaultListener;
import org.apache.cxf.message.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.kuzds.userflow.userservice.UserServicePortType;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CxfClientConfig {

    // https://docs.spring.io/spring-cloud-netflix/docs/current/reference/html/#using-the-eurekaclient

    @Value("${userflow.target-service-name}")
    private String targetServiceName;

    @Bean
    public UserServicePortType userServiceProxy(EurekaClient eurekaClient) {
        InstanceInfo instance = eurekaClient.getNextServerFromEureka(targetServiceName, false);

        JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
        jaxWsProxyFactoryBean.setServiceClass(UserServicePortType.class);
        jaxWsProxyFactoryBean.setAddress(instance.getHomePageUrl() + "/cxf/UserService");

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

