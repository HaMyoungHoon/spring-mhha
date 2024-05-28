package mhha.springmhha.model.asp

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.sql.Date

@Entity
data class UserRoleList(
        @Id
        @Column(name = "this_index")
        @get:JsonProperty("this_index")
        var thisIndex: Long = 0,
        @Column(name = "role_flag")
        @get:JsonProperty("role_flag")
        var roleFlag: Int = 0,
        @Column(name = "role_name")
        @get:JsonProperty("role_name")
        var roleName: String = "",
        @Column(name = "role_date")
        @get:JsonProperty("role_date")
        var roleDate: Date = Date(java.util.Date().time)
) {

}
