package mhha.springmhha.config

import org.apache.catalina.connector.Connector
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ConnectorConfig {
    @Value(value = "\${spring.http-port}")
    var httpPort: Int = 12581
    @Bean fun servletContainer() = TomcatServletWebServerFactory().apply {
        this.addAdditionalTomcatConnectors(createSslConnector())
    }

    private fun createSslConnector() = Connector("org.apache.coyote.http11.Http11NioProtocol").apply {
        this.scheme = "http"
        this.secure = false
        this.port = httpPort
    }
}