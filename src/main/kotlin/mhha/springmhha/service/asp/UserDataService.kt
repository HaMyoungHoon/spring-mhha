package mhha.springmhha.service.asp

import mhha.springmhha.repository.asp.UserDataRepository
import mhha.springmhha.repository.asp.UserRoleListRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserDataService {
    @Autowired lateinit var userDataRepository: UserDataRepository
    @Autowired lateinit var userRoleListRepository: UserRoleListRepository

    fun getUserData(id: String) = userDataRepository.findById(id)
    fun getAllUserData() = userDataRepository.findAllByThisIndexNot(0)

    fun getRoleList() = userRoleListRepository.findAllByThisIndexNot(0)
}