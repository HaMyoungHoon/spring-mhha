package mhha.springmhha.controller

import io.swagger.v3.oas.annotations.tags.Tag
import mhha.springmhha.config.FConstants
import mhha.springmhha.config.security.JwtTokenProvider
import mhha.springmhha.model.common.IRestResult
import mhha.springmhha.service.sqlASP.UserDataService
import mhha.springmhha.service.common.ResponseService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@Tag(name = "TestController")
@RestController
@RequestMapping(value = ["/test"])
@CrossOrigin(origins = [FConstants.HTTP_MHHA, FConstants.HTTPS_MHHA], allowedHeaders = ["*"])
class TestController {
    @Autowired lateinit var responseService: ResponseService
    @Autowired lateinit var userDataService: UserDataService

    @GetMapping(value = ["/test"])
    fun test(): IRestResult {
        return responseService.getSuccessResult()
    }
    @GetMapping(value = ["/signIn"])
    fun signIn(@RequestParam(required = true) id: String, @RequestParam(required = true) pw: String): IRestResult {
        return responseService.getResult(userDataService.signIn(id, pw))
    }
    @GetMapping(value = ["/token"])
    fun getUer(@RequestHeader(value = JwtTokenProvider.authToken) token: String): IRestResult {
        return responseService.getResult(userDataService.getUserDataToken(token))
    }
}