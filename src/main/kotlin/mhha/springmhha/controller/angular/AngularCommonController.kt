package mhha.springmhha.controller.angular

import io.swagger.v3.oas.annotations.tags.Tag
import mhha.springmhha.advice.exception.NotValidOperationException
import mhha.springmhha.model.common.IRestResult
import mhha.springmhha.model.sqlSpring.angular.doc.DocMenuItem
import mhha.springmhha.model.sqlSpring.angular.news.NewsItem
import mhha.springmhha.service.common.ResponseService
import mhha.springmhha.service.sqlSpring.AngularCommonService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
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
	fun postNews(@RequestBody data: NewsItem): IRestResult {
		return responseService.getResult(angularCommonService.addNewsItem(data))
	}

	@GetMapping(value = ["/get/doc_menu"])
	fun getDocMenu(@RequestParam(required = false) isDesc: Boolean): IRestResult {
		return responseService.getResult(angularCommonService.getDocMenuAll(isDesc))
	}
	@PostMapping(value = ["/post/doc_menu"])
	fun postDocMenu(@RequestBody data: DocMenuItem): IRestResult {
		if (data.name.isEmpty()) {
			throw NotValidOperationException()
		}
		if (angularCommonService.getDocMenu(data.name) != null) {
			throw NotValidOperationException()
		}

		data.setChild()

		return responseService.getResult(angularCommonService.addDocMenuItem(data))
	}
	@PostMapping(value = ["/post/doc_menu/list"])
	fun postDocMenuList(@RequestBody data: List<DocMenuItem>): IRestResult {
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

		return responseService.getResult(angularCommonService.addDocMenuItem(data))
	}
}