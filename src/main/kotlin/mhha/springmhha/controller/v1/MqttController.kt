package mhha.springmhha.controller.v1

import io.swagger.v3.oas.annotations.Hidden
import io.swagger.v3.oas.annotations.tags.Tag
import mhha.springmhha.service.MqttService
import mhha.springmhha.service.common.ResponseService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
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
	@GetMapping("/subscribeData")
	fun getSubscribeData() = responseService.getResult(mqttService.getMqttConnectData())
	@Hidden
	@PostMapping("/publish")
	fun postPublish(@RequestParam topic: String, @RequestParam msg: String) {
		mqttService.sendMessage(topic, msg)
	}
}