package mhha.springmhha.config

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import io.swagger.v3.oas.models.servers.Server
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@OpenAPIDefinition(servers = [io.swagger.v3.oas.annotations.servers.Server(url = "/", description = "defaultPath")])
@Configuration
class SwaggerConfiguration {
  @Value(value = "\${str.version}") lateinit var strVersion: String
  @Value(value = "\${str.profile}") lateinit var strprofile: String
  @Bean fun openApi(): OpenAPI = OpenAPI()
    .info(v1Info())
    .servers(mutableListOf<Server?>().apply {
      add(springHttpsServer())
      add(springHttpServer())
    })
  private fun v1Info() = Info()
    .title("$strprofile spring mhha")
    .description("테스트 하려고 만든 거")
    .version(strVersion)
    .license(License())
    .contact(Contact())
  private fun springHttpsServer() = Server().apply {
    url = "https://spring.mhha.kr"
    description = "https"
  }
  private fun springHttpServer() = Server().apply {
    url = "http://spring.mhha.kr"
    description = "http"
  }
}