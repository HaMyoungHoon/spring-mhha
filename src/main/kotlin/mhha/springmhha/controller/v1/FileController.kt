package mhha.springmhha.controller.v1

import io.swagger.v3.oas.annotations.tags.Tag
import mhha.springmhha.config.FConstants
import mhha.springmhha.config.security.JwtTokenProvider
import mhha.springmhha.model.common.IRestResult
import mhha.springmhha.model.common.Storage
import mhha.springmhha.service.common.ResponseService
import mhha.springmhha.service.sqlSpring.FileCommonService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@Tag(name = "FileController")
@RestController
@RequestMapping(value = ["/v1/file"])
class FileController {
	@Autowired lateinit var fileCommonService: FileCommonService
	@Autowired lateinit var responseService: ResponseService

	@PostMapping("/post/file_model")
	@CrossOrigin(origins = [FConstants.HTTP_MHHA, FConstants.HTTPS_MHHA], allowedHeaders = ["*"])
	fun postVideoFileModel(@RequestHeader(value = JwtTokenProvider.authToken) token: String,
	                       @RequestParam(required = true) fileName: String,
	                       @RequestParam(required = true) fileExt: String,
	                       @RequestParam(required = true) fileType: Storage
	): IRestResult {
		return responseService.getResult(fileCommonService.addFileModel(token, fileName, fileExt, fileType))
	}
}