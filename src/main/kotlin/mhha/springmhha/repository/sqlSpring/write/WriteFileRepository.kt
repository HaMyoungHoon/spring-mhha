package mhha.springmhha.repository.sqlSpring.write

import mhha.springmhha.model.sqlSpring.angular.write.WriteDirectory
import mhha.springmhha.model.sqlSpring.angular.write.WriteFile
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface WriteFileRepository : JpaRepository<WriteFile, Long> {
	fun findAllByWriteDirectory(writeDirectory: WriteDirectory): List<WriteFile>
	fun findByName(name: String): WriteFile?
	fun findAllByAuthIndexOrderByThisIndexDesc(authIndex: Long): List<WriteFile>?
}