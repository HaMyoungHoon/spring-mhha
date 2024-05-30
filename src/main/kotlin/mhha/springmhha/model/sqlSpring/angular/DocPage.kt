package mhha.springmhha.model.sqlSpring.angular

import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*

@Entity(name = "DocPage")
data class DocPage(
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	var thisIndex: Long = 0,
	@Column(columnDefinition = "nvarchar(100)")
	var name: String = "",
	@OneToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
	@JoinColumn
	@JsonManagedReference
	var docComponents: DocComponents? = null,
	@OneToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
	@JoinColumn
	@JsonManagedReference
	var docInterfaces: DocInterfaces? = null
) {
	fun setChild(): DocPage {
		docComponents?.docPage = this
		docInterfaces?.docPage = this
		docComponents?.setChild()
		docInterfaces?.setChild()
		return this
	}
}
