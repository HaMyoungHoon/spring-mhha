package mhha.springmhha.model.sqlSpring.angular.doc

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*

@Entity(name = "DocInterfacesEvents")
data class DocInterfacesEvents(
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	var thisIndex: Long = 0,
	@Column(columnDefinition = "nvarchar(300)")
	var description: String = "",
	@OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
	@JoinColumn
	@JsonManagedReference
	var docInterfacesEventsValues: MutableList<DocInterfacesEventsValues>? = null,
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn
	@JsonBackReference
	var docInterfaces: DocInterfaces? = null
) {
	fun setChild(): DocInterfacesEvents {
		docInterfacesEventsValues?.forEach {
			it.docInterfacesEvents = this
			it.setChild()
		}
		return this
	}
}
