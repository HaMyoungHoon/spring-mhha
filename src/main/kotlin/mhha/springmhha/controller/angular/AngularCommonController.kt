package mhha.springmhha.controller.angular

import io.swagger.v3.oas.annotations.tags.Tag
import mhha.springmhha.model.common.IRestResult
import mhha.springmhha.model.sqlSpring.angular.news.NewsItem
import mhha.springmhha.service.common.ResponseService
import mhha.springmhha.service.sqlSpring.AngularService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "AngularCommonController")
@RestController
@RequestMapping(value = ["/angular/common"])
class AngularCommonController {
	@Autowired lateinit var responseService: ResponseService
	@Autowired lateinit var angularService: AngularService

	@GetMapping(value = ["/get/news"])
	fun getNewsAll(): IRestResult {
		return responseService.getResult(angularService.getNewsAll())
	}
	@GetMapping(value = ["/get/news/one"])
	fun getNews(): IRestResult {
		return responseService.getResult(angularService.getNewsItem())
	}
	@PostMapping(value = ["/post/news"])
	fun postNews(@RequestBody data: NewsItem): IRestResult {
		return responseService.getResult(angularService.setNewsItem(data))
	}
}