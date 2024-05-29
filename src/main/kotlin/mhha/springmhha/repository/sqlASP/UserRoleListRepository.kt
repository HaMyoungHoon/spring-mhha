package mhha.springmhha.repository.sqlASP

import mhha.springmhha.model.sqlASP.UserRoleList
import org.springframework.data.repository.CrudRepository

interface UserRoleListRepository: CrudRepository<UserRoleList, Long> {
}