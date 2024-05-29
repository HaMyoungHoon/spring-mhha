package mhha.springmhha.repository.sqlSpring

import mhha.springmhha.model.sqlSpring.angular.DocComponentsEmitsValueParameters
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DCEVPRepository : JpaRepository<DocComponentsEmitsValueParameters, Long> {
}