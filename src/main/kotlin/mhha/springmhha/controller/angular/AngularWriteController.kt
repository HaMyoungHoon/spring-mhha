package mhha.springmhha.controller.angular

import io.swagger.v3.oas.annotations.tags.Tag
import mhha.springmhha.advice.exception.NotValidOperationException
import mhha.springmhha.advice.exception.ResourceAlreadyExistException
import mhha.springmhha.advice.exception.ResourceNotExistException
import mhha.springmhha.config.FConstants
import mhha.springmhha.config.security.JwtTokenProvider
import mhha.springmhha.model.common.IRestResult
import mhha.springmhha.model.sqlSpring.angular.write.WriteDirectory
import mhha.springmhha.model.sqlSpring.angular.write.WriteFile
import mhha.springmhha.service.common.ResponseService
import mhha.springmhha.service.sqlSpring.AngularCommonService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@Tag(name = "AngularWriteController")
@RestController
@RequestMapping(value = ["/angular/write"])
class AngularWriteController {
	@Autowired lateinit var responseService: ResponseService
	@Autowired lateinit var angularCommonService: AngularCommonService

	@GetMapping(value = ["/get/directory"])
	fun getDirectory(@RequestParam(required = false) isDesc: Boolean) =
		responseService.getResult(angularCommonService.getWriteDirectoryAll(isDesc))
	@GetMapping(value = ["/get/directory/name"])
	fun getDirectoryName(@RequestParam name: String) =
		responseService.getResult(angularCommonService.getWriteDirectoryName(name))
	@PostMapping(value = ["/post/directory"])
	@CrossOrigin(origins = [FConstants.HTTP_MHHA, FConstants.HTTPS_MHHA], allowedHeaders = ["*"])
	fun postDirectory(@RequestHeader(value = JwtTokenProvider.authToken) token: String,
	                  @RequestParam(required = false) parentName: String?,
	                  @RequestBody data: WriteDirectory): IRestResult {
		if (data.dirName.isEmpty()) {
			throw NotValidOperationException()
		}
		if (angularCommonService.getWriteDirectoryName(data.dirName) != null) {
			throw ResourceAlreadyExistException()
		}
		if (parentName != null) {
			val parent = angularCommonService.getWriteDirectoryName(parentName) ?: throw ResourceNotExistException()
			parent.children?.add(data)
			parent.setChild()
			return responseService.getResult(angularCommonService.addWriteDirectory(token, parent))
		}

		data.setChild()
		return responseService.getResult(angularCommonService.addWriteDirectory(token, data))
	}

	@GetMapping(value = ["/get/directory/name/files"])
	fun getDirectoryNameWithFiles(@RequestHeader(value = JwtTokenProvider.authToken, required = false) token: String?,
	                              @RequestParam name: String) =
		responseService.getResult(angularCommonService.getWriteDirectoryNameWithFile(token, name))
	@GetMapping(value = ["/get/file/all"])
	fun getWriteFileAll(@RequestHeader(value = JwtTokenProvider.authToken) token: String) =
		responseService.getResult(angularCommonService.getWriteFileAll(token))
	@GetMapping(value = ["/get/file/index"])
	fun getWriteFileIndex(@RequestHeader(value = JwtTokenProvider.authToken, required = false) token: String?,
	                      @RequestParam index: Long) =
		responseService.getResult(angularCommonService.getWriteFileIndex(token, index))
	@GetMapping(value = ["/get/file/name"])
	fun getWriteFileName(@RequestHeader(value = JwtTokenProvider.authToken, required = false) token: String?,
	                     @RequestParam name: String) =
		responseService.getResult(angularCommonService.getWriteFileName(token, name))
	@PostMapping(value = ["/post/file"])
	@CrossOrigin(origins = [FConstants.HTTP_MHHA, FConstants.HTTPS_MHHA], allowedHeaders = ["*"])
	fun postWriteFile(@RequestHeader(value = JwtTokenProvider.authToken) token: String,
	                  @RequestParam(required = true) dirName: String,
	                  @RequestBody data: WriteFile): IRestResult {
		if (data.name.isEmpty()) {
			throw NotValidOperationException()
		}
		val dir = angularCommonService.getWriteDirectoryName(dirName) ?: throw ResourceNotExistException()
		data.writeDirectory = dir
		return responseService.getResult(angularCommonService.addWriteFile(token, data))
	}
	@PutMapping(value = ["/put/file"])
	@CrossOrigin(origins = [FConstants.HTTP_MHHA, FConstants.HTTPS_MHHA], allowedHeaders = ["*"])
	fun putWriteFile(@RequestHeader(value = JwtTokenProvider.authToken) token: String,
	                 @RequestParam(required = true) fileName: String,
	                 @RequestBody content: String): IRestResult {
		val file = angularCommonService.getWriteFileName(token, fileName) ?: throw ResourceNotExistException()
		file.content = content
		return responseService.getResult(angularCommonService.editWriteFile(token, file))
	}
	@PutMapping(value = ["/put/file/move"])
	@CrossOrigin(origins = [FConstants.HTTP_MHHA, FConstants.HTTPS_MHHA], allowedHeaders = ["*"])
	fun putWriteFileMove(@RequestHeader(value = JwtTokenProvider.authToken) token: String,
	                     @RequestParam(required = true) fileName: String,
	                     @RequestParam(required = true) dirName: String): IRestResult {
		val file = angularCommonService.getWriteFileName(token, fileName) ?: throw ResourceNotExistException()
		val dir = angularCommonService.getWriteDirectoryName(dirName) ?: throw ResourceNotExistException()
		file.writeDirectory = dir
		return responseService.getResult(angularCommonService.editWriteFile(token, file))
	}
}