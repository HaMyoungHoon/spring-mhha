package mhha.springmhha.repository.sqlSpring.doc

import mhha.springmhha.model.sqlSpring.angular.doc.DocComponentsEmitsValuesParameters
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DCEVPRepository : JpaRepository<DocComponentsEmitsValuesParameters, Long> {
}