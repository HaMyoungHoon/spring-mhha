package mhha.springmhha.repository.asp

import mhha.springmhha.model.asp.UserRoleList
import org.springframework.data.repository.CrudRepository

interface UserRoleListRepository: CrudRepository<UserRoleList, Long> {
    fun findAllByThisIndexNot(thisIndex: Long): List<UserRoleList>?
}