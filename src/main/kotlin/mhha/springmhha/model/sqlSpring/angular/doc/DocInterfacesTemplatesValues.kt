package mhha.springmhha.model.sqlSpring.angular.doc

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*

@Entity(name = "DocInterfacesTemplatesValues")
data class DocInterfacesTemplatesValues(
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	var thisIndex: Long = 0,
	@Column(columnDefinition = "nvarchar(100)")
	var parent: String = "",
	@Column(columnDefinition = "nvarchar(100)")
	var name: String = "",
	@Column(columnDefinition = "nvarchar(300)")
	var description: String = "",
	@OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
	@JoinColumn
	@JsonManagedReference
	var docInterfacesTemplatesValuesParameters: MutableList<DocInterfacesTemplatesValuesParameters>? = null,
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn
	@JsonBackReference
	var docInterfacesTemplates: DocInterfacesTemplates? = null
) {
	fun setChild(): DocInterfacesTemplatesValues {
		docInterfacesTemplatesValuesParameters?.forEach {
			it.docInterfacesTemplatesValues = this
			it.setChild()
		}
		return this
	}
}