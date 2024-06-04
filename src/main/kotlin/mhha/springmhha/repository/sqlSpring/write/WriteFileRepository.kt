package mhha.springmhha.repository.sqlSpring.write

import mhha.springmhha.model.sqlSpring.angular.write.WriteFile
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface WriteFileRepository : JpaRepository<WriteFile, Long> {
}