package mhha.springmhha.model.sqlSpring.angular.write

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*

@Entity
data class WriteDirectory(
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	var thisIndex: Long = 0,
	@Column(columnDefinition = "nvarchar(300)")
	var dirName: String = "",
	@OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
	@JoinColumn
	@JsonManagedReference
	var children: MutableList<WriteDirectory>?,
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn
	@JsonBackReference
	@JsonIgnore
	var writeDirectory: WriteDirectory?,
	@OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
	@JoinColumn
	@JsonManagedReference
	var writeFiles: MutableList<WriteFile>?,
) {
	fun setChild(): WriteDirectory {
		children?.forEach {
			it.writeDirectory = this
			it.setChild()
		}
		writeFiles?.forEach {
			it.writeDirectory = this
			it.setChild()
		}
		return this
	}
}