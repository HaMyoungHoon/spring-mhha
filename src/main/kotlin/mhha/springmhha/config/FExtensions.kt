package mhha.springmhha.config

import mhha.springmhha.config.FExtensions.flag
import mhha.springmhha.model.sqlASP.UserRole
import mhha.springmhha.model.sqlASP.UserRoles
import java.util.*

object FExtensions {
	infix fun UserRoles.allOf(rhs: UserRoles) = this.containsAll(rhs)
	infix fun UserRoles.and(rhs: UserRole) = EnumSet.of(rhs, *this.toTypedArray())
	infix fun UserRoles.flag(rhs: UserRole): Int {
		val buff = this.toTypedArray()
		var ret = 0
		for (i in buff) {
			ret = ret or i.flag
		}
		ret = ret and rhs.flag

		return ret
	}
	fun UserRoles.getFlag(): Int {
		val buff = this.toTypedArray()
		var ret = 0
		for (i in buff) {
			ret = ret or i.flag
		}

		return ret
	}
}