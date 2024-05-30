package mhha.springmhha.repository.sqlSpring.doc

import mhha.springmhha.model.sqlSpring.angular.doc.DocInterfacesTemplatesValuesParameters
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DITVPRepository : JpaRepository<DocInterfacesTemplatesValuesParameters, Long> {
}