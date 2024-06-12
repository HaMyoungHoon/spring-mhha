package mhha.springmhha.service

import mhha.springmhha.advice.exception.FileDownloadException
import mhha.springmhha.advice.exception.ResourceNotExistException
import mhha.springmhha.config.FConstants
import mhha.springmhha.model.common.Storage
import mhha.springmhha.model.sqlSpring.common.FileModel
import mhha.springmhha.repository.sqlSpring.common.FileRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.context.request.async.WebAsyncTask
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody
import java.io.*
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*
import java.util.concurrent.Callable

@Service
class VideoStreamService {
	@Value(value = "\${file.defDir}")
	var defDirPath: String = ""
	@Value(value = "\${file.videoDir}")
	var videoDirPath: String = ""

	@Autowired lateinit var fileRepository: FileRepository
	fun getVideoByNameStream(fileName: String) = getVideoStreamingBodyAsync(fileRepository.findByFileName(fileName) ?: throw FileNotFoundException())
	fun getVideoByIndexStream(index: Long) = getVideoStreamingBodyAsync(fileRepository.findByThisIndex(index) ?: throw FileNotFoundException())
	fun getVideoByNameResource(fileName: String) = getVideoResourceBodyAsync(fileRepository.findByFileName(fileName) ?: throw FileNotFoundException())
	fun getVideoByIndexResource(index: Long) = getVideoResourceBodyAsync(fileRepository.findByThisIndex(index) ?: throw FileNotFoundException())
	fun getVideoByIndexByte(index: Long, range: String?) = getVideoByteArray(fileRepository.findByThisIndex(index) ?: throw FileNotFoundException(), range)
	fun getVideoByNameByte(name: String, range: String?) = getVideoByteArray(fileRepository.findByFileName(name) ?: throw FileNotFoundException(), range)
	fun getVideoStreamingBodyAsync(file: FileModel) = WebAsyncTask(Callable { getVideoStreamingBody(file) })
	fun getVideoResourceBodyAsync(file: FileModel) = WebAsyncTask(Callable { getVideoResourceBody(file) })

	private fun getVideoStreamingBody(file: FileModel): ResponseEntity<StreamingResponseBody> {
		val filePath = getFilePath(file)
		val fileSize = getFileSize(filePath)
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
	private fun getVideoResourceBody(file: FileModel): ResponseEntity<Resource> {
		val filePath = getFilePath(file)
		val fileSize = getFileSize(filePath)
		return ResponseEntity.ok()
			.header(FConstants.CONTENT_TYPE, "${FConstants.VIDEO_CONTENT}${file.fileExt}")
			.header(FConstants.CONTENT_LENGTH, fileSize.toString())
			.body(UrlResource(filePath.toUri()))
	}
	private fun getVideoByteArray(file: FileModel, range: String?): ResponseEntity<ByteArray> {
		val filePath = getFilePath(file)
		val fileSize = getFileSize(filePath)
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

	fun getFilePath(file: FileModel) = fileLocation(file.fileType).let { x ->
		if (file.subPath == null) {
			x.resolve("${file.fileName}.${file.fileExt}")
		} else {
			file.subPath.let { y ->
				x.resolve("${y}/${file.fileName}.${file.fileExt}")
			}
		}
	}
	fun getFilePath(fileName: String, enum: Storage) = fileLocation(enum).resolve(fileName)
	fun getFileSize(path: Path) = Files.size(path)

	fun fileLocation(enum: Storage) = when (enum) {
		Storage.DEF -> Paths.get(defDirPath).toAbsolutePath().normalize()
		Storage.VIDEO -> Paths.get(videoDirPath).toAbsolutePath().normalize()
		else -> throw ResourceNotExistException()
	}
	fun folderExist(enum: Storage) = Optional.ofNullable(Files.createDirectories(fileLocation(enum))).orElseThrow { FileDownloadException() }
	fun enumToInt(enum: Storage) = when (enum) {
		Storage.DEF -> 0
		Storage.VIDEO -> 1
		else -> throw ResourceNotExistException()
	}
}