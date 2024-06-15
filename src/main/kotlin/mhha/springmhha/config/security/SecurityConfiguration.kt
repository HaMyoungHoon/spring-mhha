package mhha.springmhha.config.security

import mhha.springmhha.service.sqlSpring.AngularCommonService
import mhha.springmhha.service.sqlSpring.IPControlService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication
import org.springframework.boot.autoconfigure.security.ConditionalOnDefaultWebSecurity
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.access.intercept.AuthorizationFilter
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.header.HeaderWriterFilter


@Configuration
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
@EnableWebSecurity
@ConditionalOnDefaultWebSecurity
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
class SecurityConfiguration {
  @Autowired lateinit var refererCheckFilter: RefererCheckFilter
  @Autowired lateinit var jwtTokenProvider: JwtTokenProvider
  @Autowired lateinit var ipControlService: IPControlService
  @Autowired lateinit var angularCommonService: AngularCommonService
    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain = http
      .csrf { it.disable() }
      .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
      .exceptionHandling {
        it.authenticationEntryPoint(CustomAuthenticationEntryPoint())
        it.accessDeniedHandler(CustomAccessDeniedHandler())
      }
      .addFilterBefore(refererCheckFilter, UsernamePasswordAuthenticationFilter::class.java)
//      .addFilterBefore(JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter::class.java)
      .addFilterBefore(IPFilter(ipControlService), HeaderWriterFilter::class.java)
      .addFilterAfter(LogFilter(angularCommonService), AuthorizationFilter::class.java)
      .build()

    @Bean
    fun passwordEncoder(): PasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()
}