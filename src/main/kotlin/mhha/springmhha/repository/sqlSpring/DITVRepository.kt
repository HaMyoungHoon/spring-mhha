package mhha.springmhha.repository.sqlSpring

import mhha.springmhha.model.sqlSpring.angular.DocInterfacesTemplatesValues
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DITVRepository : JpaRepository<DocInterfacesTemplatesValues, Long> {
}