package mhha.springmhha.model.sqlSpring.angular

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*

@Entity(name = "DocInterfacesEventsValuesProps")
data class DocInterfacesEventsValuesProps(
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	var thisIndex: Long = 0,
	@Column(columnDefinition = "nvarchar(100)")
	var name: String = "",
	@Column
	var optional: Boolean = false,
	@Column
	var readonly: Boolean = false,
	@Column(columnDefinition = "nvarchar(100)")
	var type: String = "",
	@Column(columnDefinition = "nvarchar(300)")
	var description: String = "",
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn
	@JsonBackReference
	var docInterfacesEventsValues: DocInterfacesEventsValues? = null
) {
	fun setChild(): DocInterfacesEventsValuesProps {
		return this
	}
}
