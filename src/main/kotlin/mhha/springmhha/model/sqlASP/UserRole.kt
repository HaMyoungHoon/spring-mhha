package mhha.springmhha.model.sqlASP

import mhha.springmhha.config.FExtensions.Companion.and
import java.util.EnumSet

enum class UserRole(var flag: Int) {
    None(0),
    Admin(1),
    Guest(Admin.flag.shl(1)),
    Readonly(Admin.flag.shl(2));

    infix fun and(rhs: UserRole) = EnumSet.of(this, rhs)
    companion object {
        fun fromInt(flag: Int) = entries.firstOrNull { it.flag == flag }
        fun fromFlag(flag: Int): UserRoles {
            var ret = UserRoles.of(None)
            entries.forEach {
                if (it.flag and flag != 0) {
                    ret = ret.and(it)
                }
            }

            return ret
        }
    }
}

internal typealias UserRoles = EnumSet<UserRole>