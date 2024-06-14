package mhha.springmhha.controller.v1

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.tags.Tag
import mhha.springmhha.advice.exception.NotValidOperationException
import mhha.springmhha.advice.exception.ResourceAlreadyExistException
import mhha.springmhha.advice.exception.ResourceNotExistException
import mhha.springmhha.config.FConstants
import mhha.springmhha.config.security.JwtTokenProvider
import mhha.springmhha.model.common.IRestResult
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
	@GetMapping("/get/category/list/{dirName}")
	@CrossOrigin(origins = [FConstants.HTTP_MHHA, FConstants.HTTPS_MHHA], allowedHeaders = ["*"])
	fun getVideoCategoryWithChild(@RequestHeader(value = JwtTokenProvider.authToken, required = false) token: String?,
	                              @PathVariable(required = true) dirName: String,
	                              @RequestParam(required = false) isDesc: Boolean?) =
		responseService.getResult(videoStreamService.getVideoCategoryWithChild(token, dirName, isDesc ?: true))
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

	@GetMapping("/get/name/stream/name/{name}")
	@CrossOrigin(origins = [FConstants.HTTP_FRONT_1, FConstants.HTTPS_FRONT_1], allowedHeaders = ["*"])
	fun getVideoNameStream(@RequestHeader(value = JwtTokenProvider.authToken, required = false) token: String?,
	                       @PathVariable(required = true) name: String) =
		videoStreamService.getVideoByNameStream(token, name)
	@GetMapping("/get/index/stream/index/{index}")
	@CrossOrigin(origins = [FConstants.HTTP_FRONT_1, FConstants.HTTPS_FRONT_1], allowedHeaders = ["*"])
	fun getVideoIndexStream(@RequestHeader(value = JwtTokenProvider.authToken, required = false) token: String?,
	                        @PathVariable(required = true) index: Long) =
		videoStreamService.getVideoByIndexStream(token, index)
	@GetMapping("/get/name/resource/name/{name}")
	@CrossOrigin(origins = [FConstants.HTTP_FRONT_1, FConstants.HTTPS_FRONT_1], allowedHeaders = ["*"])
	fun getVideoNameResource(@RequestHeader(value = JwtTokenProvider.authToken, required = false) token: String?,
	                         @PathVariable(required = true) name: String) =
		videoStreamService.getVideoByNameResource(token, name)
	@GetMapping("/get/index/resource/index/{index}")
	@CrossOrigin(origins = [FConstants.HTTP_FRONT_1, FConstants.HTTPS_FRONT_1], allowedHeaders = ["*"])
	fun getVideoIndexResource(@RequestHeader(value = JwtTokenProvider.authToken, required = false) token: String?,
	                          @PathVariable(required = true) index: Long) =
		videoStreamService.getVideoByIndexResource(token, index)
	@GetMapping("/get/name/byte/name/{name}")
	@CrossOrigin(origins = [FConstants.HTTP_FRONT_1, FConstants.HTTPS_FRONT_1], allowedHeaders = ["*"])
	fun getVideoNameByte(@RequestHeader(value = JwtTokenProvider.authToken, required = false) token: String?,
	                     @RequestHeader(value = "Range", required = false) httpRangeList: String?,
	                     @PathVariable(required = true) name: String) =
		videoStreamService.getVideoByNameByte(token, name, httpRangeList)
	@GetMapping("/get/index/byte/index/{index}")
	@CrossOrigin(origins = [FConstants.HTTP_FRONT_1, FConstants.HTTPS_FRONT_1], allowedHeaders = ["*"])
	fun getVideoIndexByte(@RequestHeader(value = JwtTokenProvider.authToken, required = false) token: String?,
	                      @RequestHeader(value = "Range", required = false) httpRangeList: String?,
	                      @PathVariable(required = true) index: String) =
		Mono.just(videoStreamService.getVideoByIndexByte(token, index.toLong(), httpRangeList))

	@PostMapping("/post/category")
	@CrossOrigin(origins = [FConstants.HTTP_MHHA, FConstants.HTTPS_MHHA], allowedHeaders = ["*"])
	fun postCategory(@RequestHeader(value = JwtTokenProvider.authToken) token: String,
	                 @Schema(description = "root/path/path 이렇게 해야함") @RequestParam(required = false) parentName: String?,
									 @RequestBody data: VideoCategoryModel): IRestResult {
		if (data.dirName.isEmpty()) {
			throw NotValidOperationException()
		}
		if (videoStreamService.getVideoCategory(data.dirName) != null) {
			throw ResourceAlreadyExistException()
		}
		if (parentName != null) {
			val dirs = parentName.split("/")
			if (dirs.isEmpty()) {
				throw ResourceNotExistException()
			}
			val rootDir = videoStreamService.getFindRootDir(dirs[0]) ?: throw ResourceNotExistException()
			var child: VideoCategoryModel = rootDir
			for (i in 1 until dirs.size) {
				val buff = child.children?.firstOrNull { x -> x.dirName == dirs[i] } ?: throw ResourceNotExistException()
				child = buff
			}
			child.children?.add(data)
			child.setChild()
			return responseService.getResult(videoStreamService.addVideoCategory(token, child))
		}

		data.setChild()
		return responseService.getResult(videoStreamService.addVideoCategory(token, data))
	}
	@PostMapping("/post/video")
	@Operation(summary = "post video path", description = "실제로 video file 을 업로드 하는 것은 아님.")
	@CrossOrigin(origins = [FConstants.HTTP_MHHA, FConstants.HTTPS_MHHA], allowedHeaders = ["*"])
	fun postVideo(@RequestHeader(value = JwtTokenProvider.authToken) token: String,
	              @Schema(description = "root/path/path 이렇게 해야함") @RequestParam(required = true) dirName: String,
	              @RequestBody data: VideoModel): IRestResult {
		if (data.fileName.isEmpty()) {
			throw NotValidOperationException()
		}
		val exist = videoStreamService.getVideoByFileNameAndSubPath(data.fileName, data.subPath)
		if (exist != null) {
			throw ResourceAlreadyExistException()
		}
		val dirs = dirName.split("/")
		if (dirs.isEmpty()) {
			throw ResourceNotExistException()
		}
		val rootDir = videoStreamService.getFindRootDir(dirs[0]) ?: throw ResourceNotExistException()
		var child: VideoCategoryModel = rootDir
		for (i in 1 until dirs.size) {
			val buff = child.children?.firstOrNull { x -> x.dirName == dirs[i] } ?: throw ResourceNotExistException()
			child = buff
		}
		data.videoCategory = child
		return responseService.getResult(videoStreamService.addVideo(token, data))
	}
}