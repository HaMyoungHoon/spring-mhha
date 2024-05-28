package mhha.springmhha.controller

import io.swagger.v3.oas.annotations.tags.Tag
import mhha.springmhha.advice.FAmhohwa
import mhha.springmhha.model.common.IRestResult
import mhha.springmhha.service.asp.UserDataService
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
    @Autowired lateinit var userDataService: UserDataService
    @Autowired lateinit var amhohwa: FAmhohwa

    @GetMapping(value = ["/test"])
    fun test(): IRestResult {
        return responseService.getResult(userDataService.getUserData("mhha"))
    }
}