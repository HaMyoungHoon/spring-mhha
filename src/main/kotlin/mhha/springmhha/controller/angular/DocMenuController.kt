package mhha.springmhha.controller.angular

import io.swagger.v3.oas.annotations.tags.Tag
import mhha.springmhha.advice.exception.NotValidOperationException
import mhha.springmhha.advice.exception.ResourceNotExistException
import mhha.springmhha.config.security.JwtTokenProvider
import mhha.springmhha.model.common.IRestResult
import mhha.springmhha.model.sqlSpring.angular.doc.*
import mhha.springmhha.service.common.ResponseService
import mhha.springmhha.service.sqlSpring.AngularCommonService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@Tag(name = "DocMenuController")
@RestController
@RequestMapping(value = ["/angular/doc"])
class DocMenuController {
    @Autowired lateinit var responseService: ResponseService
    @Autowired lateinit var angularCommonService: AngularCommonService

    @GetMapping(value = ["/get/menu"])
    fun getDocMenu(@RequestParam(required = false) isDesc: Boolean): IRestResult {
        val menu = angularCommonService.getDocMenuAll(isDesc)
        return responseService.getResult(menu?.distinct())
    }
    @PostMapping(value = ["/post/menu"])
    fun postDocMenu(@RequestHeader(value = JwtTokenProvider.authToken) token: String,
                    @RequestBody data: DocMenuItem): IRestResult {
        if (data.name.isEmpty()) {
            throw NotValidOperationException()
        }
        if (angularCommonService.getDocMenu(data.name) != null) {
            throw NotValidOperationException()
        }

        data.setChild()

        return responseService.getResult(angularCommonService.addDocMenuItem(token, data))
    }
    @PostMapping(value = ["/post/menu/list"])
    fun postDocMenuList(@RequestHeader(value = JwtTokenProvider.authToken) token: String,
                        @RequestBody data: List<DocMenuItem>): IRestResult {
        val temp = data.distinctBy { it.name }.filter { it.name.isNotEmpty() }.toMutableList()
        if (temp.isEmpty()) {
            throw NotValidOperationException()
        }

        val sameThing = angularCommonService.getDocMenu(temp.map { it.name })
        if (sameThing != null) {
            temp.removeAll { x ->
                sameThing.any { it.name == x.name }
            }
        }

        if (temp.isEmpty()) {
            throw NotValidOperationException()
        }

        temp.forEach { it.setChild() }

        return responseService.getResult(angularCommonService.addDocMenuItem(token, data))
    }
    @PostMapping(value = ["/post/menu/child"])
    fun postDocMenuChild(@RequestHeader(value = JwtTokenProvider.authToken) token: String,
                         @RequestParam name: String, @RequestBody data: DocMenuItem): IRestResult {
        if (data.name.isEmpty()) {
            throw NotValidOperationException()
        }
        val parent = angularCommonService.getDocMenu(name) ?: throw ResourceNotExistException()

        if (parent.children == null) {
            parent.children = mutableListOf()
        }
        parent.children?.add(data)
        parent.setChild()

        return responseService.getResult(angularCommonService.addDocMenuItem(token, parent))
    }
}