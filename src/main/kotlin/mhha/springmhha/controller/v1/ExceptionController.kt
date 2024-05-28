package mhha.springmhha.controller.v1

import io.swagger.v3.oas.annotations.tags.Tag
import mhha.springmhha.advice.exception.AuthenticationEntryPointException
import mhha.springmhha.model.common.IRestResult
import org.springframework.security.access.AccessDeniedException
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "ExceptionController")
@RestController
@RequestMapping(value = ["/v1/exception"])
class ExceptionController {
    @GetMapping(value = ["/entryPoint"])
    fun entrypointException(): IRestResult {
        throw AuthenticationEntryPointException()
    }

    @GetMapping(value = ["/accessDenied"])
    fun accessDeniedException(): IRestResult {
        throw AccessDeniedException("")
    }
}