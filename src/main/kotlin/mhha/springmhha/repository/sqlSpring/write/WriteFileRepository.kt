package mhha.springmhha.repository.sqlSpring.write

import mhha.springmhha.model.sqlSpring.angular.write.WriteDirectory
import mhha.springmhha.model.sqlSpring.angular.write.WriteFile
import mhha.springmhha.model.sqlSpring.angular.write.WriteFileStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface WriteFileRepository : JpaRepository<WriteFile, Long> {
	fun findAllByWriteDirectory(writeDirectory: WriteDirectory): List<WriteFile>
	fun findByThisIndex(thisIndex: Long): WriteFile?
	fun findByThisIndexAndStatusNot(thisIndex: Long, status: WriteFileStatus): WriteFile?
	fun findByName(name: String): WriteFile?
	fun findByNameAndStatusNot(name: String, status: WriteFileStatus): WriteFile?
	fun findAllByAuthIndexAndStatusNotOrderByThisIndexDesc(authIndex: Long, status: WriteFileStatus): List<WriteFile>?

	@Query("SELECT thisIndex, authIndex, '' as content, name, status, writeDirectory_thisIndex FROM WriteFILE WHERE writeDirectory_thisIndex = :writeDirectoryThisIndex ORDER BY thisIndex DESC", nativeQuery = true)
	fun findAllByWriteDirectoryWithoutContent(writeDirectoryThisIndex: Long): List<WriteFile>
	@Query("SELECT thisIndex, authIndex, '' as content, name, status, writeDirectory_thisIndex FROM WriteFILE WHERE writeDirectory_thisIndex = :writeDirectoryThisIndex AND status != :status ORDER BY thisIndex DESC", nativeQuery = true)
	fun findAllByWriteDirectoryAndStatusNotWithoutContent(writeDirectoryThisIndex: Long, status: WriteFileStatus): List<WriteFile>
}