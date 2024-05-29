package mhha.springmhha.service.sqlASP

import mhha.springmhha.repository.sqlASP.UserDataRepository
import mhha.springmhha.repository.sqlASP.UserRoleListRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserDataService {
    @Autowired lateinit var userDataRepository: UserDataRepository
    @Autowired lateinit var userRoleListRepository: UserRoleListRepository

    fun getUserData(id: String) = userDataRepository.findById(id)
    fun getAllUserData() = userDataRepository.findAll()

    fun getRoleList() = userRoleListRepository.findAll()
}