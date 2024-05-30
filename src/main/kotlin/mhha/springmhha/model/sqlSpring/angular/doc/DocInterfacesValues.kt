package mhha.springmhha.model.sqlSpring.angular.doc

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*

@Entity(name = "DocInterfacesValues")
data class DocInterfacesValues(
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
	var docInterfacesValuesProps: MutableList<DocInterfacesValuesProps>? = null,
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn
	@JsonBackReference
	var docInterfaces: DocInterfaces? = null
) {
	fun setChild(): DocInterfacesValues {
		docInterfacesValuesProps?.forEach {
			it.docInterfacesValues = this
			it.setChild()
		}
		return this
	}
}
