package mhha.springmhha.config.security

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import org.springframework.web.filter.GenericFilterBean

class JwtAuthenticationFilter(var jwtTokenProvider: JwtTokenProvider): GenericFilterBean() {
    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
//        val token = jwtTokenProvider.resolveToken(request as HttpServletRequest)
//        if (!token.isNullOrEmpty() && jwtTokenProvider.validateToken(token)) {
//            SecurityContextHolder.getContext().authentication = jwtTokenProvider.getAuthentication(token)
//        }
        chain?.doFilter(request, response)
    }
}