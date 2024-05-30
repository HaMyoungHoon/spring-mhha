package mhha.springmhha.repository.sqlSpring.doc

import mhha.springmhha.model.sqlSpring.angular.doc.DocPage
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DocPageRepository : JpaRepository<DocPage, Long> {
	fun findByThisIndex(thisIndex: Long): DocPage?
	fun findByName(name: String): DocPage?
}