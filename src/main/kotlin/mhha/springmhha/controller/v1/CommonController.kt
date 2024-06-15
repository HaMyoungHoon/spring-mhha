package mhha.springmhha.controller.v1

import io.swagger.v3.oas.annotations.tags.Tag
import mhha.springmhha.config.FConstants
import mhha.springmhha.model.common.IRestResult
import mhha.springmhha.service.common.ResponseService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "CommonController")
@RestController
@RequestMapping(value = ["/v1"])
@CrossOrigin(origins = [FConstants.HTTP_MHHA, FConstants.HTTPS_MHHA], allowedHeaders = ["*"])
class CommonController {
	@Autowired lateinit var responseService: ResponseService
	@Value(value = "\${str.version}") lateinit var strVersion: String
	@Value(value = "\${str.profile}") lateinit var strprofile: String

	@GetMapping(value = ["/version"])
	fun version(): IRestResult {
		return responseService.getResult("$strprofile $strVersion")
	}
}