package mhha.springmhha.repository.sqlSpring

import mhha.springmhha.model.sqlSpring.angular.DocInterfacesTemplatesValuesParameters
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DITVPRepository : JpaRepository<DocInterfacesTemplatesValuesParameters, Long> {
}