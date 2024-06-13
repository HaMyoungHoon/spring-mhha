package mhha.springmhha.model.sqlSpring.common

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*

@Entity
data class VideoCategoryModel(
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	var thisIndex: Long = 0,
	@Column
	var videoCategoryState: VideoCategoryState = VideoCategoryState.OK,
	@Column(columnDefinition = "nvarchar(300)")
	var dirName: String = "",
	@OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
	@JoinColumn
	@JsonManagedReference
	var children: MutableList<VideoCategoryModel>? = null,
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn
	@JsonBackReference
	@JsonIgnore
	var videoCategory: VideoCategoryModel? = null,
	@OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
	@JoinColumn
	@JsonManagedReference
	var video: MutableList<VideoModel>? = null,
) {
	fun setChild(): VideoCategoryModel {
		children?.forEach {
			it.videoCategory = this
			it.setChild()
		}
		video?.forEach {
			it.videoCategory = this
			it.setChild()
		}
		return this
	}
	fun init() {
		video = mutableListOf()
		children?.forEach {
			it.init()
		}
	}
}
