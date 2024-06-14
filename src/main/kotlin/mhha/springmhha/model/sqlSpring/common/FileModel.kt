package mhha.springmhha.model.sqlSpring.common

import jakarta.persistence.*
import mhha.springmhha.model.common.Storage

@Entity
data class FileModel(
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	var thisIndex: Long = 0,
	@Column(columnDefinition = "nvarchar(300)")
	var fileName: String = "",
	@Column(columnDefinition = "nvarchar(100)")
	var fileExt: String = "",
	@Column
	var fileType: Storage = Storage.DEF,
	@Column(columnDefinition = "nvarchar(300)")
	var subPath: String? = null,
	@Column
	var fileState: FileState = FileState.OK
)