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
	@Column(columnDefinition = "nvarchar(100)", unique = true)
	var name: String,
	@Column(columnDefinition = "nvarchar(100)")
	var icon: String?,
	@Column(columnDefinition = "nvarchar(300)")
	var routerLink: String?,
	@Column(columnDefinition = "nvarchar(300)")
	var href: String?,
	@Column(columnDefinition = "nvarchar(100)")
	var badge: String?,
	@Column
	var menuState: DocMenuState = DocMenuState.OK,
	@OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
	@JoinColumn(name = "children_thisIndex")
	@JsonManagedReference
	var children: MutableList<DocMenuItem>?,
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "docMenuItem_thisIndex")
	@JsonBackReference
	@JsonIgnore
	var docMenuItem: DocMenuItem?,
) {
	fun setChild(): DocMenuItem {
		children?.forEach {
			it.docMenuItem = this
			it.setChild()
		}
		return this
	}
	fun removeChildMenuStateNot(menuState: DocMenuState) {
		children?.forEach { x ->
			x.removeChildMenuStateNot(menuState)
		}
		children?.removeAll { x ->
			x.menuState != menuState
		}
	}
	fun removeChildMenuState(menuState: DocMenuState) {
		children?.forEach { x ->
			x.removeChildMenuState(menuState)
		}
		children?.removeAll { x ->
			x.menuState == menuState
		}
	}
}
