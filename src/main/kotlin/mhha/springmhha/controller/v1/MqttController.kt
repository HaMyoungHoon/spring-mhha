package mhha.springmhha.controller.v1

import io.swagger.v3.oas.annotations.Hidden
import io.swagger.v3.oas.annotations.tags.Tag
import mhha.springmhha.config.security.JwtTokenProvider
import mhha.springmhha.model.common.IRestResult
import mhha.springmhha.service.MqttService
import mhha.springmhha.service.common.ResponseService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "MqttController")
@RestController
@RequestMapping(value = ["/v1/mqtt"])
class MqttController {
	@Autowired lateinit var responseService: ResponseService
	@Autowired lateinit var mqttService: MqttService

	@Hidden
	@GetMapping(value = ["/get/subscribeData/{guid}", "/get/subscribeData"])
	fun getSubscribeData(@RequestHeader(value = JwtTokenProvider.authToken, required = false) token: String?,
	                     @PathVariable(value = "guid", required = false) guid: String?) =
		responseService.getResult(mqttService.getMqttConnectData(token, guid))
	@PostMapping(value = ["/post/publish/{guid}", "/post/publish"])
	fun postPublish(@RequestHeader(value = JwtTokenProvider.authToken, required = false) token: String?,
	                @PathVariable(value = "guid", required = false) guid: String?,
	                @RequestParam topic: String, @RequestParam msg: String): IRestResult {
		if (token == null && guid == null) {
			return responseService.getFailResult(401, "need to token or guid")
		}
		mqttService.sendMessage(token, guid, topic, msg)
		return responseService.getSuccessResult()
	}
}