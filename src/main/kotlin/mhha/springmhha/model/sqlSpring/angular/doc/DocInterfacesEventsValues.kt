package mhha.springmhha.model.sqlSpring.angular.doc

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*

@Entity(name = "DocInterfacesEventsValues")
data class DocInterfacesEventsValues(
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	var thisIndex: Long = 0,
	@Column(columnDefinition = "nvarchar(100)")
	var name: String = "",
	@Column(columnDefinition = "nvarchar(300)")
	var description: String = "",
	@OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
	@JoinColumn
	@JsonManagedReference
	var docInterfacesEventsValuesProps: MutableList<DocInterfacesEventsValuesProps>? = null,
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn
	@JsonBackReference
	var docInterfacesEvents: DocInterfacesEvents? = null
) {
	fun setChild(): DocInterfacesEventsValues {
		docInterfacesEventsValuesProps?.forEach {
			it.docInterfacesEventsValues = this
			it.setChild()
		}
		return this
	}
}
