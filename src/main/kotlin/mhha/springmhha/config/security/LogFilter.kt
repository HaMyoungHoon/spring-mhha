package mhha.springmhha.config.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import mhha.springmhha.model.sqlSpring.common.LogModel
import mhha.springmhha.service.sqlSpring.AngularCommonService
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.util.ContentCachingRequestWrapper
import org.springframework.web.util.ContentCachingResponseWrapper

class LogFilter(private var angularCommonService: AngularCommonService): OncePerRequestFilter() {
	override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
		val cachingRequestWrapper = ContentCachingRequestWrapper(request)
		request.let { x ->
			angularCommonService.addLog(LogModel().apply {
				setRequestWrapper(cachingRequestWrapper)
			})
			filterChain.doFilter(request, response)
		}
	}
}