package mhha.springmhha.repository.sqlSpring.common

import mhha.springmhha.model.sqlSpring.common.LogModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.sql.Timestamp

@Repository
interface LogRepository : JpaRepository<LogModel, Long> {
	fun findByLocalAddrAndRequestUriAndDateTimeGreaterThan(localAddr: String, requestUri: String, dateTime: Timestamp): LogModel?
}