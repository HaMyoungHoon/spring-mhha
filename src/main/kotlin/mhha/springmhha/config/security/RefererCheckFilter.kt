package mhha.springmhha.config.security

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpFilter
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import mhha.springmhha.config.FConstants
import org.springframework.stereotype.Component
import java.io.IOException

@Component
class RefererCheckFilter : HttpFilter() {
	var strProfile: String = ""
	@Throws(IOException::class, ServletException::class)
	override fun doFilter(request: HttpServletRequest?, response: HttpServletResponse?, chain: FilterChain?) {
		if (strProfile == "dev") {
			chain?.doFilter(request, response)
			return
		}
		val requestUrl = request?.requestURL
		if (requestUrl == null || !requestUrl.contains("v1/video/get/play")) {
			chain?.doFilter(request, response)
			return
		}

		val referer = request.getHeader("Referer")
		if (refererStartWithFront(referer)) {
			chain?.doFilter(request, response)
			return
		}

		response?.sendRedirect("/v1/exception/accessDenied")
	}

	fun refererStartWithFront(referer: String): Boolean {
		if (referer.startsWith(FConstants.HTTP_FRONT_1) || referer.startsWith(FConstants.HTTPS_FRONT_1)) {
			return true
		}
		if (referer.startsWith(FConstants.HTTP_FRONT_2) || referer.startsWith(FConstants.HTTPS_FRONT_2)) {
			return true
		}
		if (referer.startsWith(FConstants.HTTP_FRONT_3) || referer.startsWith(FConstants.HTTPS_FRONT_3)) {
			return true
		}

		return false
	}

	fun setStrProfile(strProfile: String): RefererCheckFilter {
		this.strProfile = strProfile
		return this
	}
}