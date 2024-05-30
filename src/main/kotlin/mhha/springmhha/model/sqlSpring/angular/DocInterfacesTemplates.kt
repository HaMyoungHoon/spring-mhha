package mhha.springmhha.model.sqlSpring.angular

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*

@Entity(name = "DocInterfacesTemplates")
data class DocInterfacesTemplates(
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	var thisIndex: Long = 0,
	@Column(columnDefinition = "nvarchar(300)")
	var description: String = "",
	@OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
	@JoinColumn
	@JsonManagedReference
	var docInterfacesTemplatesValues: MutableList<DocInterfacesTemplatesValues>? = null,
	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn
	@JsonBackReference
	var docInterfaces: DocInterfaces? = null
) {
	fun setChild(): DocInterfacesTemplates {
		docInterfacesTemplatesValues?.forEach {
			it.docInterfacesTemplates = this
			it.setChild()
		}
		return this
	}
}