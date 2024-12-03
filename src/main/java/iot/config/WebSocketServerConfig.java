package iot.config;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebSocketServerConfig {

    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> customWebServerFactory() {
        return factory -> {
            factory.addAdditionalTomcatConnectors(createWebSocketConnector());
        };
    }

    private Connector createWebSocketConnector() {
        Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
        connector.setPort(8081); // WebSocket 포트
        return connector;
    }
}