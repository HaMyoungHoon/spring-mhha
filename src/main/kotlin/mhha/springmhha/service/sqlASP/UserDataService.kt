package mhha.springmhha.service.sqlASP

import mhha.springmhha.advice.FAmhohwa
import mhha.springmhha.advice.exception.AuthenticationEntryPointException
import mhha.springmhha.advice.exception.SignInFailedException
import mhha.springmhha.config.security.JwtTokenProvider
import mhha.springmhha.model.sqlASP.UserData
import mhha.springmhha.repository.sqlASP.UserDataRepository
import mhha.springmhha.repository.sqlASP.UserRoleListRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserDataService {
    @Autowired lateinit var jwtTokenProvider: JwtTokenProvider
    @Autowired lateinit var userDataRepository: UserDataRepository
    @Autowired lateinit var userRoleListRepository: UserRoleListRepository
    @Autowired lateinit var fAmhohwa: FAmhohwa

    fun getUserData(id: String) = userDataRepository.findById(id)
    fun getUserDataToken(token: String): UserData {
        if (!jwtTokenProvider.validateToken(token)) {
            throw AuthenticationEntryPointException()
        }
        return jwtTokenProvider.getUserData(token)
    }
    fun getAllUserData() = userDataRepository.findAll()

    fun getRoleList() = userRoleListRepository.findAll()

    fun signIn(id: String, pw: String): String {
        val userData = getUserData(id) ?: throw SignInFailedException()
        val encryptPW = fAmhohwa.encrypt(pw)
        if (userData.password != encryptPW) {
            throw SignInFailedException()
        }

        return jwtTokenProvider.createToken(userData)
    }
}