package mhha.springmhha.repository.sqlSpring.common

import mhha.springmhha.model.sqlSpring.common.IPBlockModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface IPBlockRepository : JpaRepository<IPBlockModel, Long> {
	fun findByIpAddr(ipAddr: String): IPBlockModel?
}