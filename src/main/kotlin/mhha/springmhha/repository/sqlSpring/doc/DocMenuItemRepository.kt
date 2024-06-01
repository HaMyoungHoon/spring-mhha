package mhha.springmhha.repository.sqlSpring.doc

import mhha.springmhha.model.sqlSpring.angular.doc.DocMenuItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DocMenuItemRepository : JpaRepository<DocMenuItem, Long> {
	fun findAllByOrderByThisIndexAsc(): List<DocMenuItem>?
	fun findAllByOrderByThisIndexDesc(): List<DocMenuItem>?
	fun findByName(name: String): DocMenuItem?
	fun findAllByNameIn(name: List<String>): List<DocMenuItem>?
}