package mhha.springmhha.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class SwaggerConfiguration {
    @Bean fun openApi(): OpenAPI = OpenAPI().info(v1Info())
    private fun v1Info() = Info()
            .title("spring mhha")
            .description("테스트 하려고 만든 거")
            .version("1")
            .license(License())
            .contact(Contact())
}