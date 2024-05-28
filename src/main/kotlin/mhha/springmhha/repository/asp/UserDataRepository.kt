package mhha.springmhha.repository.asp

import mhha.springmhha.model.asp.UserData
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserDataRepository : JpaRepository<UserData, Long> {
    fun findAllByThisIndexNot(thisIndex: Long): List<UserData>?
    fun findById(id: String): UserData?
}