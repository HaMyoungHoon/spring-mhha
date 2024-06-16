package mhha.springmhha.service

import mhha.springmhha.advice.exception.AuthenticationEntryPointException
import mhha.springmhha.config.FConstants
import mhha.springmhha.config.FExtensions
import mhha.springmhha.config.jpa.SpringJPAConfig
import mhha.springmhha.config.security.JwtTokenProvider
import mhha.springmhha.model.sqlASP.UserRole
import mhha.springmhha.model.sqlSpring.common.*
import mhha.springmhha.repository.sqlSpring.common.VideoCategoryRepository
import mhha.springmhha.repository.sqlSpring.common.VideoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.context.request.async.WebAsyncTask
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody
import java.io.*
import java.nio.file.Files
import java.nio.file.Path
import java.util.concurrent.Callable

@Service
class VideoStreamService {
	@Autowired lateinit var videoCategoryRepository: VideoCategoryRepository
	@Autowired lateinit var videoRepository: VideoRepository
	@Autowired lateinit var jwtTokenProvider: JwtTokenProvider
	@Autowired lateinit var fExt: FExtensions

	fun getFindRootOnlyRoot(token: String?, isDesc: Boolean = true) =
		if(isAdmin(token, false)) videoCategoryRepository.findRoot()?.onEach { x -> x.children = null; x.init() }
		else videoCategoryRepository.findRootStateOK()?.onEach { x -> x.children = null; x.init() }
	fun getFindRootDir(dirName: String) = videoCategoryRepository.findRootDirName(dirName)
	fun getVideoCategory(thisIndex: Long) = videoCategoryRepository.findByThisIndex(thisIndex)?.apply { init() }
	fun getVideoCategory(dirName: String) = videoCategoryRepository.findByDirName(dirName)?.apply { init() }
	fun getVideoCategoryWithVideo(dirName: String) = videoCategoryRepository.findByDirName(dirName)?.apply { getVideoList(this) }
	fun getVideoCategoryList(token: String?, isDesc: Boolean = true) =
		if (isAdmin(token, false)) getVideoCategoryList(isDesc)
		else getVideoCategoryListByStateNotDelete(isDesc)
	private fun getVideoCategoryList(isDesc: Boolean = true) =
		if (isDesc) videoCategoryRepository.findAllByVideoCategoryOrderByThisIndexDesc(null)?.onEach { x -> x.init() }
		else videoCategoryRepository.findAllByVideoCategoryOrderByThisIndexAsc(null)?.onEach { x -> x.init() }
	private fun getVideoCategoryListByStateNotDelete(isDesc: Boolean) =
		if (isDesc) videoCategoryRepository.findAllByVideoCategoryAndVideoCategoryStateNotOrderByThisIndexDesc(null, VideoCategoryState.DISABLE)?.onEach { x -> x.children?.removeAll { y -> y.videoCategoryState == VideoCategoryState.DISABLE}; x.init() }
		else videoCategoryRepository.findAllByVideoCategoryAndVideoCategoryStateNotOrderByThisIndexAsc(null, VideoCategoryState.DISABLE)?.onEach { x -> x.children?.removeAll { y -> y.videoCategoryState == VideoCategoryState.DISABLE}; x.init() }
	fun getVideoCategoryWithChild(token: String?, thisIndex: Long, isDesc: Boolean = true) =
		if (isAdmin(token, false)) videoCategoryRepository.findByThisIndex(thisIndex)?.apply { setVideoCategoryVideo(token, this, isDesc) }
		else videoCategoryRepository.findByVideoCategoryStateAndThisIndex(VideoCategoryState.OK, thisIndex)?.apply { setVideoCategoryVideo(token, this, isDesc) }
	private fun setVideoCategoryVideo(token: String?, videoCategoryModel: VideoCategoryModel, isDesc: Boolean = true) {
		videoCategoryModel.video = getVideoList(token, videoCategoryModel, isDesc)?.toMutableList()
		videoCategoryModel.children?.onEach { x -> setVideoCategoryVideo(token, x, isDesc) }
	}
	private fun getVideoList(token: String?, videoCategoryModel: VideoCategoryModel, isDesc: Boolean = false) =
		if (isAdmin(token, false)) getVideoList(videoCategoryModel, isDesc)
		else getVideoListByStateOK(videoCategoryModel, isDesc)
	private fun getVideoList(videoCategoryModel: VideoCategoryModel, isDesc: Boolean = false) =
		if (isDesc) videoRepository.findAllByVideoCategoryOrderByFileDateDesc(videoCategoryModel)
		else videoRepository.findAllByVideoCategoryOrderByFileDateAsc(videoCategoryModel)
	private fun getVideoListByStateOK(videoCategoryModel: VideoCategoryModel, isDesc: Boolean = false) =
		if (isDesc) videoRepository.findAllByFileStateAndVideoCategoryOrderByFileDateDesc(FileState.OK, videoCategoryModel)
		else videoRepository.findAllByFileStateAndVideoCategoryOrderByFileDateAsc(FileState.OK, videoCategoryModel)

	fun getVideoByFileNameAndSubPath(fileName: String, subPath: String?) = videoRepository.findByFileNameAndSubPath(fileName, subPath)
	fun getVideoList(token: String?, isDesc: Boolean = false) =
		if (isAdmin(token, false)) getVideoList(isDesc)
		else getVideoListByStateOK(isDesc)
	private fun getVideoList(isDesc: Boolean = false) =
		if (isDesc) videoRepository.findAllByOrderByFileDateDesc()
		else videoRepository.findAllByOrderByFileDateAsc()
	private fun getVideoListByStateOK(isDesc: Boolean = false) =
		if (isDesc) videoRepository.findAllByFileStateOrderByFileDateDesc(FileState.OK)
		else videoRepository.findAllByFileStateOrderByFileDateAsc(FileState.OK)

	fun searchVideoByFileName(token: String?, fileName: String, isDesc: Boolean = false) =
		if (isAdmin(token, false)) searchVideoByFileName(fileName, isDesc)
		else searchVideoByFileNameByStateOK(fileName, isDesc)
	private fun searchVideoByFileName(fileName: String, isDesc: Boolean = false) =
		if (isDesc) videoRepository.findAllByFileNameContainingOrderByFileDateDesc(fileName)
		else videoRepository.findAllByFileNameContainingOrderByFileDateAsc(fileName)
	private fun searchVideoByFileNameByStateOK(fileName: String, isDesc: Boolean = false) =
		if (isDesc) videoRepository.findAllByFileStateAndFileNameContainingOrderByFileDateDesc(FileState.OK, fileName)
		else videoRepository.findAllByFileStateAndFileNameContainingOrderByFileDateAsc(FileState.OK, fileName)

	fun searchVideoByTitle(token: String?, title: String, isDesc: Boolean = false) =
		if (isAdmin(token, false)) searchVideoByTitle(title, isDesc)
		else searchVideoByTitleByStateOK(title, isDesc)
	private fun searchVideoByTitle(title: String, isDesc: Boolean = false) =
		if (isDesc) videoRepository.findAllByTitleContainingOrderByFileDateDesc(title)
		else videoRepository.findAllByTitleContainingOrderByFileDateAsc(title)
	private fun searchVideoByTitleByStateOK(title: String, isDesc: Boolean = false) =
		if (isDesc) videoRepository.findAllByFileStateAndTitleContainingOrderByFileDateDesc(FileState.OK, title)
		else videoRepository.findAllByFileStateAndTitleContainingOrderByFileDateAsc(FileState.OK, title)

	fun searchVideoByHasTag(token: String?, hashTag: String, isDesc: Boolean = false) =
		if (isAdmin(token, false)) searchVideoByHashTag(hashTag, isDesc)
		else searchVideoByHashTagByStateOK(hashTag, isDesc)
	private fun searchVideoByHashTag(hashTag: String, isDesc: Boolean = false) =
		if (isDesc) videoRepository.findAllByHashTagContainingOrderByFileDateDesc(hashTag)
		else videoRepository.findAllByHashTagContainingOrderByFileDateAsc(hashTag)
	private fun searchVideoByHashTagByStateOK(hashTag: String, isDesc: Boolean = false) =
		if (isDesc) videoRepository.findAllByFileStateAndHashTagContainingOrderByFileDateDesc(FileState.OK, hashTag)
		else videoRepository.findAllByFileStateAndHashTagContainingOrderByFileDateAsc(FileState.OK, hashTag)

	fun searchVideo(token: String?, searchString: String, isDesc: Boolean = false) =
		if (isAdmin(token, false)) searchVideo(searchString, isDesc)
		else searchVideoByStateOK(searchString, isDesc)
	private fun searchVideo(searchString: String, isDesc: Boolean = false) =
		if (isDesc) videoRepository.findAllByFileNameContainingOrTitleContainingOrHashTagContainingOrderByFileDateDesc(searchString, searchString, searchString)
		else videoRepository.findAllByFileNameContainingOrTitleContainingOrHashTagContainingOrderByFileDateAsc(searchString, searchString, searchString)
	private fun searchVideoByStateOK(searchString: String, isDesc: Boolean = false) =
		if (isDesc) videoRepository.findAllByFileNameContainingOrTitleContainingOrHashTagContainingOrderByFileDateDesc(searchString, searchString, searchString)?.apply { removeAll { x -> x.fileState != FileState.OK } }
		else videoRepository.findAllByFileNameContainingOrTitleContainingOrHashTagContainingOrderByFileDateAsc(searchString, searchString, searchString)?.apply { removeAll { x -> x.fileState != FileState.OK } }

	fun getVideoByNameStream(token: String?, fileName: String) = getVideoStreamingBodyAsync(
		if (isAdmin(token, false)) videoRepository.findByFileName(fileName)
		else videoRepository.findByFileStateAndFileName(FileState.OK, fileName))
	fun getVideoByIndexStream(token: String?, index: Long) = getVideoStreamingBodyAsync(
		if (isAdmin(token, false)) videoRepository.findByThisIndex(index)
		else videoRepository.findByFileStateAndThisIndex(FileState.OK, index))
	fun getVideoByNameResource(token: String?, fileName: String) = getVideoResourceBodyAsync(
		if (isAdmin(token, false)) videoRepository.findByFileName(fileName)
		else videoRepository.findByFileStateAndFileName(FileState.OK, fileName))
	fun getVideoByIndexResource(token: String?, index: Long) = getVideoResourceBodyAsync(
		if (isAdmin(token, false)) videoRepository.findByThisIndex(index)
		else videoRepository.findByFileStateAndThisIndex(FileState.OK, index))
	fun getVideoByIndexByte(token: String?, index: Long, range: String?): ResponseEntity<ByteArray> {
		val file = if (isAdmin(token, false)) videoRepository.findByThisIndex(index)
		else videoRepository.findByFileStateAndThisIndex(FileState.OK, index)
		return if (file == null) getEmptyByteArray() else getVideoByteArray(file, range)
	}
	fun getVideoByNameByte(token: String?, fileName: String, range: String?): ResponseEntity<ByteArray> {
		val file = if (isAdmin(token, false)) videoRepository.findByFileName(fileName)
		else videoRepository.findByFileStateAndFileName(FileState.OK, fileName)
		return if (file == null) getEmptyByteArray() else getVideoByteArray(file, range)
	}
	fun getVideoStreamingBodyAsync(file: VideoModel?) = WebAsyncTask(Callable { if (file == null) getEmptyStreamingBody() else getVideoStreamingBody(file) })
	fun getVideoResourceBodyAsync(file: VideoModel?) = WebAsyncTask(Callable { if (file == null) getEmptyResourceBody() else getVideoResourceBody(file) })

	private fun getEmptyStreamingBody() = ResponseEntity.ok()
		.header(FConstants.CONTENT_TYPE, "${FConstants.VIDEO_CONTENT}mp4")
		.header(FConstants.CONTENT_LENGTH, "0")
		.body(StreamingResponseBody { })
	private fun getVideoStreamingBody(file: VideoModel): ResponseEntity<StreamingResponseBody> {
		val filePath = fExt.getFilePath(file)
		val fileSize = fExt.getFileSize(filePath)
		if (!Files.exists(filePath)) {
			throw FileNotFoundException()
		}
		val streamingResponseBody = StreamingResponseBody { x ->
			val inputStream = FileInputStream(File(filePath.toAbsolutePath().normalize().toString()))
			val byteArray = ByteArray(FConstants.BYTE_RANGE)
			var length = inputStream.read(byteArray)
			while (length >= 0) {
				x.write(byteArray, 0, length)
				length = inputStream.read(byteArray)
			}
			inputStream.close()
			x.flush()
		}

		return ResponseEntity.ok()
			.header(FConstants.CONTENT_TYPE, "${FConstants.VIDEO_CONTENT}${file.fileExt}")
			.header(FConstants.CONTENT_LENGTH, fileSize.toString())
			.body(streamingResponseBody)
	}
	private fun getEmptyResourceBody(): ResponseEntity<Resource> {
		val filePath = fExt.getFilePath(VideoModel().apply {
			fileName = "NULL"
			fileExt = "mp4"
		})
		val fileSize = fExt.getFileSize(filePath)
		if (!Files.exists(filePath)) {
			throw FileNotFoundException()
		}
		return ResponseEntity.ok()
			.header(FConstants.CONTENT_TYPE, "${FConstants.VIDEO_CONTENT}mp4")
			.header(FConstants.CONTENT_LENGTH, fileSize.toString())
			.body(UrlResource(filePath.toUri()))
	}
	private fun getVideoResourceBody(file: VideoModel): ResponseEntity<Resource> {
		val filePath = fExt.getFilePath(file)
		val fileSize = fExt.getFileSize(filePath)
		if (!Files.exists(filePath)) {
			throw FileNotFoundException()
		}
		return ResponseEntity.ok()
			.header(FConstants.CONTENT_TYPE, "${FConstants.VIDEO_CONTENT}${file.fileExt}")
			.header(FConstants.CONTENT_LENGTH, fileSize.toString())
			.body(UrlResource(filePath.toUri()))
	}
	private fun getEmptyByteArray(): ResponseEntity<ByteArray> {
		return ResponseEntity.ok()
			.header(FConstants.CONTENT_TYPE, "${FConstants.VIDEO_CONTENT}mp4")
			.header(FConstants.ACCEPT_RANGES, FConstants.BYTES)
			.header(FConstants.CONTENT_LENGTH, "0")
			.header(FConstants.CONTENT_RANGE, "${FConstants.BYTES} 0-0/0")
			.body(ByteArray(0))
	}
	private fun getVideoByteArray(file: VideoModel, range: String?): ResponseEntity<ByteArray> {
		val filePath = fExt.getFilePath(file)
		val fileSize = fExt.getFileSize(filePath)
		if (!Files.exists(filePath)) {
			throw FileNotFoundException()
		}
		var rangeStart = 0L
		var rangeEnd = FConstants.CHUNK_SIZE
		if (range == null) {
			return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
				.header(FConstants.CONTENT_TYPE, "${FConstants.VIDEO_CONTENT}${file.fileExt}")
				.header(FConstants.ACCEPT_RANGES, FConstants.BYTES)
				.header(FConstants.CONTENT_RANGE, "${FConstants.BYTES} 0-${rangeEnd}/${fileSize}")
				.header(FConstants.CONTENT_LENGTH, fileSize.toString())
				.body(readByteRangeNew(filePath, rangeStart, rangeEnd))
		}
		val ranges = range.split("-")
		if (range.isNotEmpty() && ranges[0].length > 6) {
			rangeStart = ranges[0].substring(6).toLongOrNull() ?: 0
			rangeEnd = if (ranges.size > 1 && ranges[1].isNotEmpty()) {
				ranges[1].toLongOrNull() ?: FConstants.CHUNK_SIZE
			} else {
				rangeStart + FConstants.CHUNK_SIZE
			}
		}

		if (rangeStart > rangeEnd) {
			val temp = rangeEnd
			rangeEnd = rangeStart
			rangeStart = temp
		}

		rangeEnd = rangeEnd.coerceAtMost(fileSize - 1)
		val data = readByteRangeNew(filePath, rangeStart, rangeEnd)
		val contentLength = (rangeEnd - rangeStart) + 1
		val httpStatus = if (rangeEnd >= fileSize) {
			HttpStatus.OK
		} else {
			HttpStatus.PARTIAL_CONTENT
		}
		return ResponseEntity.status(httpStatus)
			.header(FConstants.CONTENT_TYPE, "${FConstants.VIDEO_CONTENT}${file.fileExt}")
			.header(FConstants.ACCEPT_RANGES, FConstants.BYTES)
			.header(FConstants.CONTENT_LENGTH, contentLength.toString())
			.header(FConstants.CONTENT_RANGE, "${FConstants.BYTES} ${rangeStart}-${rangeEnd}/${fileSize}")
			.body(data)
	}

	@Transactional(SpringJPAConfig.TRANSACTION_MANAGER)
	fun addVideoCategory(token: String?, data: VideoCategoryModel): VideoCategoryModel {
		isAdmin(token)
		return videoCategoryRepository.save(data)
	}
	@Transactional(SpringJPAConfig.TRANSACTION_MANAGER)
	fun addVideo(token: String?, data: VideoModel): VideoModel {
		data.hashTag = data.hashTag.replace(" ", "_")
		isAdmin(token)
		return videoRepository.save(data)
	}

	fun readByteRangeNew(filePath: Path, start: Long, end: Long): ByteArray {
		val data = Files.readAllBytes(filePath)
		val ret = ByteArray((end - start).toInt() + 1)
		System.arraycopy(data, start.toInt(), ret, 0, ret.size)
		return ret
	}
	fun readByteRange(filePath: Path, start: Long, end: Long, fileSize: Long): ByteArray {
		val inputStream = Files.newInputStream(filePath)
		val bufferedOutputStream = ByteArrayOutputStream()
		var rangeEnd = end
		rangeEnd = rangeEnd.coerceAtMost(fileSize)
		val data = ByteArray((rangeEnd - start).toInt() + 1)
		inputStream.read(data, 0, data.size)
		bufferedOutputStream.writeBytes(data)
		bufferedOutputStream.flush()
		val ret = ByteArray((end - start).toInt() + 1)
		System.arraycopy(bufferedOutputStream, start.toInt(), ret, 0, ret.size)
		return ret
	}

	fun isAdmin(token: String?, notAdminThrow: Boolean = true): Boolean =
		token?.let { x ->
			val user = jwtTokenProvider.getUserData(x)
			if (UserRole.fromFlag(user.role).contains(UserRole.Admin)) true
			else if (notAdminThrow) throw AuthenticationEntryPointException()
			else false
		} ?: if (notAdminThrow) throw AuthenticationEntryPointException() else false
}