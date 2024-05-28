package mhha.springmhha.model.asp

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import mhha.springmhha.model.common.CustomUserModel
import java.sql.Date

@Entity
data class UserData(
        @Id
        @Column(name = "this_index")
        @get:JsonProperty("this_index")
        var thisIndex: Long = 0,
        var id: String = "",
        var password: String = "",
        var name: String = "",
        var mail: String = "",
        var role: UserRole = UserRole.None,
        var status: UserStatus = UserStatus.None,
        @Column(name = "reg_date")
        @get:JsonProperty("reg_date")
        var regDate: Date = Date(java.util.Date().time),
        @Column(name = "last_login_date")
        @get:JsonProperty("last_login_date")
        var lastLoginDate: Date? = null
) {
    constructor(thisIndex: Long, id: String, name: String, mail: String) : this() {
        this.thisIndex = thisIndex
        this.id = id
        this.name = name
        this.mail = mail
    }
    fun setData(data: UserData) {
        this.name = data.name
        this.mail = data.mail
        this.role = data.role
        this.status = data.status
        this.regDate = data.regDate
        this.lastLoginDate = data.lastLoginDate
    }

    fun convertUserDetail(): CustomUserModel = CustomUserModel().apply {
        this.thisIndex = this@UserData.thisIndex
        this.id = this@UserData.id
        this.pw = this@UserData.password
        this.name = this@UserData.name
        this.mail = this@UserData.mail
        this.role = this@UserData.role
        this.status = this@UserData.status
        this.regDate = this@UserData.regDate
        this.lastLoginDate = this@UserData.lastLoginDate
    }
}