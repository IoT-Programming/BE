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
            // SSL을 사용하는 Connector 설정
            Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
            connector.setPort(8081); // WSS 포트
            connector.setSecure(true);
            connector.setScheme("https");

            // keystore 설정
            connector.setProperty("keystoreFile", "file:/app/keystore.p12");  // keystore 위치
            connector.setProperty("keystorePass", "keypass");  // keystore 비밀번호
            connector.setProperty("keyAlias", "tomcat"); // 키 별칭 (필요에 따라 설정)
            connector.setProperty("keystoreType", "PKCS12");
            connector.setProperty("sslProtocol", "TLS");

            // WebSocket over SSL을 위해 SSL Connector 추가
            factory.addAdditionalTomcatConnectors(connector);
        };
    }

}
