package mhha.springmhha.repository.sqlASP

import mhha.springmhha.model.sqlASP.UserData
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserDataRepository : JpaRepository<UserData, Long> {
    fun findById(id: String): UserData?
}