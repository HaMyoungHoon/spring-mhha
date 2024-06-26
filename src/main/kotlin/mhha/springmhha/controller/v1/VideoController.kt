package mhha.springmhha.controller.v1

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.tags.Tag
import mhha.springmhha.advice.exception.NotValidOperationException
import mhha.springmhha.advice.exception.ResourceAlreadyExistException
import mhha.springmhha.advice.exception.ResourceNotExistException
import mhha.springmhha.config.FConstants
import mhha.springmhha.config.security.JwtTokenProvider
import mhha.springmhha.model.sqlSpring.common.VideoCategoryModel
import mhha.springmhha.model.sqlSpring.common.VideoModel
import mhha.springmhha.service.VideoStreamService
import mhha.springmhha.service.common.ResponseService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@Tag(name = "VideoController")
@RestController
@RequestMapping(value = ["/v1/video"])
class VideoController {
	@Autowired lateinit var videoStreamService: VideoStreamService
	@Autowired lateinit var responseService: ResponseService

	@GetMapping("/get/category/list")
	fun getVideoCategoryList(@RequestHeader(value = JwtTokenProvider.authToken, required = false) token: String?,
													 @RequestParam(required = false) isDesc: Boolean?) =
		responseService.getResult(videoStreamService.getVideoCategoryList(token, isDesc ?: true))
	@GetMapping("/get/category/list/root")
	fun getVideoCategoryRootList(@RequestHeader(value = JwtTokenProvider.authToken, required = false) token: String?,
	                             @RequestParam(required = false) isDesc: Boolean?) =
		responseService.getResult(videoStreamService.getFindRootOnlyRoot(token, isDesc ?: true))
	@GetMapping("/get/category/list/withVideo")
	@CrossOrigin(origins = [FConstants.HTTP_MHHA, FConstants.HTTPS_MHHA], allowedHeaders = ["*"])
	fun getVideoCategoryWithChild(@RequestHeader(value = JwtTokenProvider.authToken, required = false) token: String?,
	                              @Schema(description = "root/path/path 이렇게 해야함") @RequestParam(required = true) dirName: String,
	                              @RequestParam(required = false) isDesc: Boolean?) =
		responseService.getResult(videoStreamService.getVideoCategoryWithChild(token, getFindRootChild(dirName).thisIndex, isDesc ?: true))
	@GetMapping("/get/video/list")
	@CrossOrigin(origins = [FConstants.HTTP_MHHA, FConstants.HTTPS_MHHA], allowedHeaders = ["*"])
	fun getVideoList(@RequestHeader(value = JwtTokenProvider.authToken, required = false) token: String?,
	                 @RequestParam(required = false) isDesc: Boolean?) =
		responseService.getResult(videoStreamService.getVideoList(token, isDesc ?: false))
	@GetMapping("/get/video/list/fileName/{fileName}")
	@CrossOrigin(origins = [FConstants.HTTP_MHHA, FConstants.HTTPS_MHHA], allowedHeaders = ["*"])
	fun searchVideoByFileName(@RequestHeader(value = JwtTokenProvider.authToken, required = false) token: String?,
	                          @PathVariable(required = true) fileName: String,
	                          @RequestParam(required = false) isDesc: Boolean?) =
		responseService.getResult(videoStreamService.searchVideoByFileName(token, fileName, isDesc ?: false))
	@GetMapping("/get/video/list/title/{title}")
	@CrossOrigin(origins = [FConstants.HTTP_MHHA, FConstants.HTTPS_MHHA], allowedHeaders = ["*"])
	fun searchVideoByTitle(@RequestHeader(value = JwtTokenProvider.authToken, required = false) token: String?,
	                       @PathVariable(required = true) title: String,
	                       @RequestParam(required = false) isDesc: Boolean?) =
		responseService.getResult(videoStreamService.searchVideoByTitle(token, title, isDesc ?: false))
	@GetMapping("/get/video/list/hashTag/{hashTag}")
	@CrossOrigin(origins = [FConstants.HTTP_MHHA, FConstants.HTTPS_MHHA], allowedHeaders = ["*"])
	fun searchVideoByHasTag(@RequestHeader(value = JwtTokenProvider.authToken, required = false) token: String?,
	                        @PathVariable(required = true) hashTag: String,
	                        @RequestParam(required = false) isDesc: Boolean?) =
		responseService.getResult(videoStreamService.searchVideoByHasTag(token, hashTag, isDesc ?: false))
	@GetMapping("/get/video/list/searchString/{searchString}")
	@CrossOrigin(origins = [FConstants.HTTP_MHHA, FConstants.HTTPS_MHHA], allowedHeaders = ["*"])
	fun searchVideo(@RequestHeader(value = JwtTokenProvider.authToken, required = false) token: String?,
	                @PathVariable(required = true) searchString: String,
	                @RequestParam(required = false) isDesc: Boolean?) =
		responseService.getResult(videoStreamService.searchVideo(token, searchString, isDesc ?: false))

	@GetMapping(value = ["/get/play/stream/name/{name}/{${JwtTokenProvider.authToken}}", "/get/play/stream/name/{name}"])
	@CrossOrigin(origins = [FConstants.HTTP_FRONT_1, FConstants.HTTPS_FRONT_1], allowedHeaders = ["*"])
	fun getVideoNameStream(@PathVariable(value = JwtTokenProvider.authToken, required = false) token: String?,
	                       @PathVariable(required = true) name: String) =
		videoStreamService.getVideoByNameStream(token, name)
	@GetMapping(value = ["/get/play/stream/index/{index}/{${JwtTokenProvider.authToken}}", "/get/play/stream/index/{index}"])
	@CrossOrigin(origins = [FConstants.HTTP_FRONT_1, FConstants.HTTPS_FRONT_1], allowedHeaders = ["*"])
	fun getVideoIndexStream(@PathVariable(value = JwtTokenProvider.authToken, required = false) token: String?,
	                        @PathVariable(required = true) index: Long) =
		videoStreamService.getVideoByIndexStream(token, index)
	@GetMapping(value = ["/get/play/resource/name/{name}/{${JwtTokenProvider.authToken}}", "/get/play/resource/name/{name}"])
	@CrossOrigin(origins = [FConstants.HTTP_FRONT_1, FConstants.HTTPS_FRONT_1], allowedHeaders = ["*"])
	fun getVideoNameResource(@PathVariable(value = JwtTokenProvider.authToken, required = false) token: String?,
	                         @PathVariable(required = true) name: String) =
		videoStreamService.getVideoByNameResource(token, name)
	@GetMapping(value = ["/get/play/resource/index/{index}/{${JwtTokenProvider.authToken}}", "/get/play/resource/index/{index}"])
	@CrossOrigin(origins = [FConstants.HTTP_FRONT_1, FConstants.HTTPS_FRONT_1], allowedHeaders = ["*"])
	fun getVideoIndexResource(@PathVariable(value = JwtTokenProvider.authToken, required = false) token: String?,
	                          @PathVariable(required = true) index: Long) =
		videoStreamService.getVideoByIndexResource(token, index)
	@GetMapping(value = ["/get/play/byte/name/{name}/{${JwtTokenProvider.authToken}}", "/get/play/byte/name/{name}"])
	@CrossOrigin(origins = [FConstants.HTTP_FRONT_1, FConstants.HTTPS_FRONT_1], allowedHeaders = ["*"])
	fun getVideoNameByte(@PathVariable(value = JwtTokenProvider.authToken, required = false) token: String?,
	                     @RequestHeader(value = "Range", required = false) httpRangeList: String?,
	                     @PathVariable(required = true) name: String) =
		videoStreamService.getVideoByNameByte(token, name, httpRangeList)
	@GetMapping(value = ["/get/play/byte/index/{index}/{${JwtTokenProvider.authToken}}", "/get/play/byte/index/{index}"])
	@CrossOrigin(origins = [FConstants.HTTP_FRONT_1, FConstants.HTTPS_FRONT_1], allowedHeaders = ["*"])
	fun getVideoIndexByte(@PathVariable(value = JwtTokenProvider.authToken, required = false) token: String?,
	                      @RequestHeader(value = "Range", required = false) httpRangeList: String?,
	                      @PathVariable(required = true) index: String) =
		Mono.just(videoStreamService.getVideoByIndexByte(token, index.toLong(), httpRangeList))

	@PostMapping("/post/category")
	@CrossOrigin(origins = [FConstants.HTTP_MHHA, FConstants.HTTPS_MHHA], allowedHeaders = ["*"])
	fun postCategory(@RequestHeader(value = JwtTokenProvider.authToken) token: String,
	                 @Schema(description = "root/path/path 이렇게 해야함") @RequestParam(required = false) parentName: String?,
									 @RequestBody data: VideoCategoryModel) =
		if (data.dirName.isEmpty()) throw NotValidOperationException()
		else responseService.getResult(videoStreamService.addVideoCategory(token,
			if (parentName != null) getFindRootChild(parentName).apply {
				if (this.children?.any { x -> x.dirName == data.dirName } == true) throw ResourceAlreadyExistException()
				children?.add(data)
				setChild()
			}
			else data.setChild()))
	@PostMapping("/post/video")
	@Operation(summary = "post video path", description = "실제로 video file 을 업로드 하는 것은 아님.")
	@CrossOrigin(origins = [FConstants.HTTP_MHHA, FConstants.HTTPS_MHHA], allowedHeaders = ["*"])
	fun postVideo(@RequestHeader(value = JwtTokenProvider.authToken) token: String,
	              @Schema(description = "root/path/path 이렇게 해야함") @RequestParam(required = true) dirName: String,
	              @RequestBody data: VideoModel) =
		if (data.fileName.isEmpty()) throw NotValidOperationException()
		else if (videoStreamService.getVideoByFileNameAndSubPath(data.fileName, data.subPath) != null) throw ResourceAlreadyExistException()
		else responseService.getResult(videoStreamService.addVideo(token, getFindRootChild(dirName).let { x -> data.videoCategory = x; data}))

	private fun getFindRootChild(dirName: String) =
		dirName.split("/").let { x ->
			if (x.isEmpty()) throw ResourceNotExistException()
			val rootDir = videoStreamService.getFindRootDir(x[0]) ?: throw ResourceNotExistException()
			var child = rootDir
			for (i in 1 until x.size) {
				val buff = child.children?.firstOrNull { y -> y.dirName == x[i] } ?: throw ResourceNotExistException()
				child = buff
			}
			child.init()
			child
		}
}