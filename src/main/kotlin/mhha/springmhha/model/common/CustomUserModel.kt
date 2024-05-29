package mhha.springmhha.model.common

import mhha.springmhha.model.sqlASP.UserRole
import mhha.springmhha.model.sqlASP.UserStatus
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.sql.Date

data class CustomUserModel(
        var thisIndex: Long = 0,
        var id: String = "",
        var pw: String = "",
        var name: String = "",
        var mail: String = "",
        var role: UserRole = UserRole.None,
        var status: UserStatus = UserStatus.None,
        var regDate: Date = Date(java.util.Date().time),
        var lastLoginDate: Date? = null
): UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = mutableListOf<SimpleGrantedAuthority>().apply {
        add(SimpleGrantedAuthority(role.name))
    }
    override fun getPassword() = this.pw
    override fun getUsername() = this.name
}