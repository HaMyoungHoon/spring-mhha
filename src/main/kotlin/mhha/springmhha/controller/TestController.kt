package mhha.springmhha.controller

import io.swagger.v3.oas.annotations.tags.Tag
import mhha.springmhha.model.common.IRestResult
import mhha.springmhha.service.sqlASP.UserDataService
import mhha.springmhha.service.common.ResponseService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "TestController")
@RestController
@RequestMapping(value = ["/test"])
class TestController {
    @Autowired lateinit var responseService: ResponseService

    @GetMapping(value = ["/test"])
    fun test(): IRestResult {
        return responseService.getSuccessResult()
    }
}