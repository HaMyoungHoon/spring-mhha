package mhha.springmhha.controller.angular

import io.swagger.v3.oas.annotations.tags.Tag
import mhha.springmhha.advice.exception.NotValidOperationException
import mhha.springmhha.advice.exception.ResourceNotExistException
import mhha.springmhha.config.security.JwtTokenProvider
import mhha.springmhha.model.common.IRestResult
import mhha.springmhha.model.sqlSpring.angular.doc.DocMenuItem
import mhha.springmhha.model.sqlSpring.angular.news.NewsItem
import mhha.springmhha.model.sqlSpring.angular.write.WriteDirectory
import mhha.springmhha.model.sqlSpring.angular.write.WriteFile
import mhha.springmhha.service.common.ResponseService
import mhha.springmhha.service.sqlSpring.AngularCommonService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "AngularCommonController")
@RestController
@RequestMapping(value = ["/angular/common"])
class AngularCommonController {
	@Autowired lateinit var responseService: ResponseService
	@Autowired lateinit var angularCommonService: AngularCommonService

	@GetMapping(value = ["/get/news"])
	fun getNewsAll(): IRestResult {
		return responseService.getResult(angularCommonService.getNewsAll())
	}
	@GetMapping(value = ["/get/news/one"])
	fun getNews(): IRestResult {
		return responseService.getResult(angularCommonService.getNewsItem())
	}
	@PostMapping(value = ["/post/news"])
	fun postNews(@RequestHeader(value = JwtTokenProvider.authToken) token: String,
	             @RequestBody data: NewsItem): IRestResult {
		return responseService.getResult(angularCommonService.addNewsItem(token, data))
	}
}