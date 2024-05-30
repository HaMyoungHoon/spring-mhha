package mhha.springmhha.model.sqlSpring.angular

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*

@Entity(name = "DocInterfacesTemplatesValuesParameters")
data class DocInterfacesTemplatesValuesParameters(
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	var thisIndex: Long = 0,
	@Column(columnDefinition = "nvarchar(100)")
	var name: String = "",
	@Column(columnDefinition = "nvarchar(300)")
	var type: String = "",
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn
	@JsonBackReference
	var docInterfacesTemplatesValues: DocInterfacesTemplatesValues? = null
) {
	fun setChild(): DocInterfacesTemplatesValuesParameters {
		return this
	}
}