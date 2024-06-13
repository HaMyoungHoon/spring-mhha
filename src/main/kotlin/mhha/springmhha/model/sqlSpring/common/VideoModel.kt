package mhha.springmhha.model.sqlSpring.common

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import java.sql.Timestamp

@Entity
data class VideoModel(
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	var thisIndex: Long = 0,
	@Column
	var fileState: FileState = FileState.OK,
	@Column(columnDefinition = "nvarchar(300)")
	var fileName: String = "",
	@Column(columnDefinition = "nvarchar(100)")
	var fileExt: String = "",
	@Column(columnDefinition = "nvarchar(300)")
	var title: String = "",
	@Column(columnDefinition = "nvarchar(300)")
	var descriptions: String? = null,
	@Column
	var fileDate: Timestamp? = null,
	@Column(columnDefinition = "text")
	var hashTag: String = "",
	@Column(columnDefinition = "nvarchar(300)")
	var subPath: String? = null,
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn
	@JsonBackReference
	var videoCategory: VideoCategoryModel?,
	@Column(insertable = false, updatable = false, name = "videoCategory_thisIndex")
	var videoCategoryThisIndex: Long?,
) {
	fun setChild(): VideoModel {
		return this
	}
}