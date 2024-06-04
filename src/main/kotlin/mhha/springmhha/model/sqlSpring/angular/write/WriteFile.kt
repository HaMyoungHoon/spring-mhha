package mhha.springmhha.model.sqlSpring.angular.write

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*

@Entity
data class WriteFile(
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	var thisIndex: Long = 0,
	@Column(columnDefinition = "nvarchar(300)")
	var name: String,
	@Column(columnDefinition = "text")
	var content: String?,
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn
	@JsonBackReference
	var writeDirectory: WriteDirectory?
) {
	fun setChild(): WriteFile {
		return this
	}
}