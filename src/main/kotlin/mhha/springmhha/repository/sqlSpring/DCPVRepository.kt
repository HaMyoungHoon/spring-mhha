package mhha.springmhha.repository.sqlSpring

import mhha.springmhha.model.sqlSpring.angular.DocComponentsPropsValues
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DCPVRepository : JpaRepository<DocComponentsPropsValues, Long> {
}