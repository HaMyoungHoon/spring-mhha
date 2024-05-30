package mhha.springmhha.repository.sqlSpring

import mhha.springmhha.model.sqlSpring.angular.DocInterfacesEventsValuesProps
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DIEVPRepository : JpaRepository<DocInterfacesEventsValuesProps, Long> {
}