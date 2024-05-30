package mhha.springmhha.repository.sqlSpring

import mhha.springmhha.model.sqlSpring.angular.DocComponentsEmitsValues
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DCEVRepository : JpaRepository<DocComponentsEmitsValues, Long> {
}