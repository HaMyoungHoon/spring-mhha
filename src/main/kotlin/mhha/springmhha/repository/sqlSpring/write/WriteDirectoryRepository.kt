package mhha.springmhha.repository.sqlSpring.write

import mhha.springmhha.model.sqlSpring.angular.write.WriteDirectory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface WriteDirectoryRepository: JpaRepository<WriteDirectory, Long> {
	fun findAllByWriteDirectoryOrderByThisIndexDesc(writeDirectory: WriteDirectory?): List<WriteDirectory>?
	fun findAllByWriteDirectoryOrderByThisIndexAsc(writeDirectory: WriteDirectory?): List<WriteDirectory>?
	fun findByDirName(name: String): WriteDirectory?
	fun findByThisIndex(thisIndex: Long): WriteDirectory?
}