package mhha.springmhha.config.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import mhha.springmhha.service.sqlSpring.IPControlService
import org.springframework.web.filter.OncePerRequestFilter

class IPFilter(private var ipControlService: IPControlService): OncePerRequestFilter() {
	override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
		filterChain.doFilter(request, response)
	}
}