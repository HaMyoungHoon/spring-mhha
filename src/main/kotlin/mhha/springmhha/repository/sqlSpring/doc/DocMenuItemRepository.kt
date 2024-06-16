package mhha.springmhha.repository.sqlSpring.doc

import mhha.springmhha.model.sqlSpring.angular.doc.DocMenuItem
import mhha.springmhha.model.sqlSpring.angular.doc.DocMenuState
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DocMenuItemRepository : JpaRepository<DocMenuItem, Long> {
	fun findAllByDocMenuItemOrderByThisIndexDesc(data: DocMenuItem?): List<DocMenuItem>?
	fun findAllByDocMenuItemOrderByThisIndexAsc(data: DocMenuItem?): List<DocMenuItem>?
	fun findAllByMenuStateAndDocMenuItemOrderByThisIndexDesc(menuState: DocMenuState, data: DocMenuItem?): MutableList<DocMenuItem>?
	fun findAllByMenuStateAndDocMenuItemOrderByThisIndexAsc(menuState: DocMenuState, data: DocMenuItem?): MutableList<DocMenuItem>?
	fun findAllByOrderByThisIndexDesc(): List<DocMenuItem>?
	fun findAllByMenuStateOrderByThisIndexAsc(menuState: DocMenuState): List<DocMenuItem>?
	fun findAllByMenuStateOrderByThisIndexDesc(menuState: DocMenuState): List<DocMenuItem>?
	fun findByName(name: String): DocMenuItem?
	fun findByMenuStateAndName(menuState: DocMenuState, name: String): DocMenuItem?
	fun findAllByNameIn(name: List<String>): List<DocMenuItem>?
}