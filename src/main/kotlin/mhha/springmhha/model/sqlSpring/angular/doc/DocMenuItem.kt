package mhha.springmhha.model.sqlSpring.angular.doc

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*

@Entity(name = "DocMenuItem")
data class DocMenuItem(
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	var thisIndex: Long = 0,
	@Column(columnDefinition = "nvarchar(100)")
	var name: String,
	@Column(columnDefinition = "nvarchar(100)")
	var icon: String?,
	@Column(columnDefinition = "nvarchar(300)")
	var routerLink: String?,
	@Column(columnDefinition = "nvarchar(300)")
	var href: String?,
	@Column(columnDefinition = "nvarchar(100)")
	var badge: String?,
	@OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
	@JoinColumn
	@JsonManagedReference
	var children: MutableList<DocMenuItem>?,
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn
	@JsonBackReference
	@JsonIgnore
	var docMenuItem: DocMenuItem?
) {
	fun setChild(): DocMenuItem {
		children?.forEach {
			it.docMenuItem = this
			it.setChild()
		}
		return this
	}
}
