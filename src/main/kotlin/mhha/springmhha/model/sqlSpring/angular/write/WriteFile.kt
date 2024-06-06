package mhha.springmhha.model.sqlSpring.angular.write

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*

@Entity
data class WriteFile(
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	var thisIndex: Long = 0,
	@Column(unique = true, nullable = false, columnDefinition = "nvarchar(300)")
	var name: String = "",
	@Column(columnDefinition = "text")
	var content: String?,
	@Column
	var authIndex: Long = 0,
	@Column
	var status: WriteFileStatus = WriteFileStatus.None,
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn
	@JsonBackReference
	var writeDirectory: WriteDirectory?,
	@Column(insertable = false, updatable = false, name = "writeDirectory_thisIndex")
	var writeDirectoryThisIndex: Long?
) {
	fun setChild(): WriteFile {
		return this
	}
}