package mhha.springmhha.repository.sqlSpring.common

import mhha.springmhha.model.common.Storage
import mhha.springmhha.model.sqlSpring.common.FileModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface FileRepository : JpaRepository<FileModel, Long> {
	fun findByFileName(fileName: String): FileModel?
	fun findByFileNameAndFileExtAndFileType(fileName: String, fileExt: String, fileType: Storage): FileModel?
	fun findByThisIndex(thisIndex: Long): FileModel?

	@Query("SELECT ret.thisIndex, ret.fileName, ret.fileExt, ret.fileType, ret.subPath, ret.fileState FROM (\n" +
			"SELECT ROW_NUMBER() OVER(ORDER BY thisIndex ASC) AS rownum, \n" +
			"thisIndex, fileName, fileExt, fileType, subPath, fileState FROM FileModel) \n" +
			"ret WHERE ret.rownum BETWEEN :startCount AND :endCount AND ret.fileType = :fileType ORDER BY ret.thisIndex ASC", nativeQuery = true)
	fun findStartEndCount(startCount: Int, endCount: Int, fileType: Storage): List<FileModel>?
}