package mhha.springmhha.controller.v1

import io.swagger.v3.oas.annotations.tags.Tag
import mhha.springmhha.config.security.JwtTokenProvider
import mhha.springmhha.model.common.IRestResult
import mhha.springmhha.model.common.Storage
import mhha.springmhha.service.VideoStreamService
import mhha.springmhha.service.common.ResponseService
import mhha.springmhha.service.sqlSpring.FileCommonService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.Resource
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.async.WebAsyncTask
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody
import reactor.core.publisher.Mono

@Tag(name = "VideoController")
@RestController
@RequestMapping(value = ["/v1/video"])
class VideoController {
	@Autowired lateinit var fileCommonService: FileCommonService
	@Autowired lateinit var videoStreamService: VideoStreamService
	@Autowired lateinit var responseService: ResponseService

	@GetMapping("/get/list")
	fun getVideList(@RequestParam(required = false) startCount: Int?, @RequestParam(required = false) endCount: Int?): IRestResult {
		return responseService.getResult(fileCommonService.getFileModel(startCount, endCount, Storage.VIDEO))
	}
	@GetMapping("/get/name/stream/{name}")
	fun getVideoNameStream(@RequestHeader(value = JwtTokenProvider.authToken, required = false) token: String?,
	                       @PathVariable(required = true) name: String): WebAsyncTask<ResponseEntity<StreamingResponseBody>> {
		return videoStreamService.getVideoByNameStream(name)
	}
	@GetMapping("/get/index/stream/{index}")
	fun getVideoIndexStream(@RequestHeader(value = JwtTokenProvider.authToken, required = false) token: String?,
	                        @PathVariable(required = true) index: Long): WebAsyncTask<ResponseEntity<StreamingResponseBody>> {
		return videoStreamService.getVideoByIndexStream(index)
	}
	@GetMapping("/get/name/resource/{name}")
	fun getVideoNameResource(@RequestHeader(value = JwtTokenProvider.authToken, required = false) token: String?,
	                         @PathVariable(required = true) name: String): WebAsyncTask<ResponseEntity<Resource>> {
		return videoStreamService.getVideoByNameResource(name)
	}
	@GetMapping("/get/index/resource/{index}")
	fun getVideoIndexResource(@RequestHeader(value = JwtTokenProvider.authToken, required = false) token: String?,
	                          @PathVariable(required = true) index: Long): WebAsyncTask<ResponseEntity<Resource>> {
		return videoStreamService.getVideoByIndexResource(index)
	}
	@GetMapping("/get/name/byte/{name}")
	fun getVideoNameByte(@RequestHeader(value = JwtTokenProvider.authToken, required = false) token: String?,
	                            @RequestHeader(value = "Range", required = false) httpRangeList: String?,
	                            @PathVariable(required = true) name: String): ResponseEntity<ByteArray> {
		return videoStreamService.getVideoByNameByte(name, httpRangeList)
	}
	@GetMapping("/get/index/byte/{index}")
	fun getVideoIndexByte(@RequestHeader(value = JwtTokenProvider.authToken, required = false) token: String?,
	                      @RequestHeader(value = "Range", required = false) httpRangeList: String?,
	                      @PathVariable(required = true) index: String): Mono<ResponseEntity<ByteArray>> {
		return Mono.just(videoStreamService.getVideoByIndexByte(index.toLong(), httpRangeList))
	}

	@PostMapping("/post/file_model")
	fun postVideoFileModel(@RequestHeader(value = JwtTokenProvider.authToken) token: String,
	                       @RequestParam(required = true) fileName: String,
	                       @RequestParam(required = true) fileExt: String,
												 @RequestParam(required = true) fileType: Storage): IRestResult {
		return responseService.getResult(fileCommonService.addFileModel(token, fileName, fileExt, fileType))
	}
}