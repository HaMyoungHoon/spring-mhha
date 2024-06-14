package mhha.springmhha.config

import mhha.springmhha.advice.exception.FileDownloadException
import mhha.springmhha.advice.exception.ResourceNotExistException
import mhha.springmhha.model.common.Storage
import mhha.springmhha.model.sqlASP.UserRole
import mhha.springmhha.model.sqlASP.UserRoles
import mhha.springmhha.model.sqlSpring.common.FileModel
import mhha.springmhha.model.sqlSpring.common.VideoModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*

@Component
class FExtensions {
	@Value(value = "\${file.defDir}") lateinit var defDirPath: String
	@Value(value = "\${file.videoDir}") lateinit var  videoDirPath: String
	@Value(value = "\${file.imageDir}") lateinit var  imageDirPath: String
	@Value(value = "\${file.documentDir}") lateinit var  documentDirPath: String
	@Value(value = "\${file.musicDir}") lateinit var  musicDirPath: String

	companion object {
		infix fun UserRoles.allOf(rhs: UserRoles) = this.containsAll(rhs)
		infix fun UserRoles.and(rhs: UserRole) = EnumSet.of(rhs, *this.toTypedArray())
		infix fun UserRoles.flag(rhs: UserRole): Int {
			val buff = this.toTypedArray()
			var ret = 0
			for (i in buff) {
				ret = ret or i.flag
			}
			ret = ret and rhs.flag

			return ret
		}
		fun UserRoles.getFlag(): Int {
			val buff = this.toTypedArray()
			var ret = 0
			for (i in buff) {
				ret = ret or i.flag
			}

			return ret
		}
	}

	fun getFilePath(file: VideoModel) = fileLocation(Storage.VIDEO).let { x ->
		if (file.subPath == null) {
			x.resolve("${file.fileName}.${file.fileExt}")
		} else {
			file.subPath.let { y ->
				x.resolve("${y}/${file.fileName}.${file.fileExt}")
			}
		}
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
		Storage.IMAGE -> Paths.get(imageDirPath).toAbsolutePath().normalize()
		Storage.DOCUMENT -> Paths.get(documentDirPath).toAbsolutePath().normalize()
		Storage.MUSIC -> Paths.get(musicDirPath).toAbsolutePath().normalize()
		else -> throw ResourceNotExistException()
	}
	fun folderExist(enum: Storage) = Optional.ofNullable(Files.createDirectories(fileLocation(enum))).orElseThrow { FileDownloadException() }
	fun enumToInt(enum: Storage) = when (enum) {
		Storage.DEF -> 0
		Storage.VIDEO -> 1
		Storage.IMAGE -> 2
		Storage.DOCUMENT -> 3
		Storage.MUSIC -> 4
		else -> throw ResourceNotExistException()
	}
}