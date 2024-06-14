package mhha.springmhha.config

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpFilter
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import java.io.IOException

@Component
class RefererCheckFilter : HttpFilter() {
	@Throws(IOException::class, ServletException::class)
	override fun doFilter(request: HttpServletRequest?, response: HttpServletResponse?, chain: FilterChain?) {
		val requestUrl = request?.requestURL
		if (requestUrl == null || !requestUrl.contains("v1/video/get/play")) {
			chain?.doFilter(request, response)
			return
		}

		val referer = request.getHeader("Referer")
		if (referer.startsWith(FConstants.HTTP_FRONT_1) || referer.startsWith(FConstants.HTTPS_FRONT_1)) {
			chain?.doFilter(request, response)
			return
		}

		response?.sendRedirect("/v1/exception/accessDenied")
	}
}