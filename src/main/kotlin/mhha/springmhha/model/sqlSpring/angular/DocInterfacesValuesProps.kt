package mhha.springmhha.model.sqlSpring.angular

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*

@Entity(name = "DocInterfacesValuesProps")
data class DocInterfacesValuesProps(
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
	var docInterfacesValues: DocInterfacesValues? = null
) {
	fun setChild(): DocInterfacesValuesProps {
		return this
	}
}
