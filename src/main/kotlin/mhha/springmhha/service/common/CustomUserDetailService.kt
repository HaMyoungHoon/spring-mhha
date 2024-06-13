package mhha.springmhha.service.common

import mhha.springmhha.advice.exception.UserNotFoundException
import mhha.springmhha.model.common.CustomUserModel
import mhha.springmhha.repository.sqlASP.UserDataRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class CustomUserDetailService: UserDetailsService {
    @Autowired lateinit var userDataRepository: UserDataRepository
    override fun loadUserByUsername(id: String): CustomUserModel =
            Optional.ofNullable(userDataRepository.findById(id)).orElseThrow { UserNotFoundException() }.apply {
            }.convertUserDetail()
}