package mhha.springmhha.model.common

import mhha.springmhha.model.sqlASP.UserRole
import mhha.springmhha.model.sqlASP.UserRoles
import mhha.springmhha.model.sqlASP.UserStatus
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.sql.Timestamp
import java.util.stream.Collectors

data class CustomUserModel(
  var thisIndex: Long = 0,
  var id: String = "",
  var pw: String = "",
  var name: String = "",
  var mail: String = "",
  var role: UserRoles = UserRoles.of(UserRole.None),
  var status: UserStatus = UserStatus.None,
  var regDate: Timestamp = Timestamp(java.util.Date().time),
  var lastLoginDate: Timestamp? = null
): UserDetails {
  override fun getAuthorities(): MutableCollection<out GrantedAuthority> = role.stream().map { SimpleGrantedAuthority(it.name) }.collect(Collectors.toList())
  override fun getPassword() = this.pw
  override fun getUsername() = this.name
}