package mhha.springmhha.model.sqlSpring.angular

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*

@Entity(name = "DocInterfaces")
data class DocInterfaces(
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	var thisIndex: Long = 0,
	@Column(columnDefinition = "nvarchar(300)")
	var description: String? = null,
	@OneToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
	@JoinColumn
	@JsonManagedReference
	var docInterfacesEvents: DocInterfacesEvents? = null,
	@OneToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
	@JoinColumn
	@JsonManagedReference
	var docInterfacesTemplates: DocInterfacesTemplates? = null,
	@OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
	@JoinColumn
	@JsonManagedReference
	var docInterfacesValues: MutableList<DocInterfacesValues>? = null,
	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn
	@JsonBackReference
	var docPage: DocPage? = null
) {
	fun setChild(): DocInterfaces {
		docInterfacesEvents?.docInterfaces = this
		docInterfacesTemplates?.docInterfaces = this
		docInterfacesValues?.forEach {
			it.docInterfaces = this
			it.setChild()
		}
		docInterfacesEvents?.setChild()
		docInterfacesTemplates?.setChild()
		return this
	}
}
