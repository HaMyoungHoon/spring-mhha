package mhha.springmhha.repository.sqlSpring.common

import mhha.springmhha.model.sqlSpring.common.FileState
import mhha.springmhha.model.sqlSpring.common.VideoCategoryModel
import mhha.springmhha.model.sqlSpring.common.VideoModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface VideoRepository: JpaRepository<VideoModel, Long> {
	fun findAllByOrderByFileDateDesc(): List<VideoModel>?
	fun findAllByOrderByFileDateAsc(): List<VideoModel>?
	fun findAllByFileStateOrderByFileDateDesc(fileState: FileState): List<VideoModel>?
	fun findAllByFileStateOrderByFileDateAsc(fileState: FileState): List<VideoModel>?
	fun findAllByVideoCategoryOrderByFileDateDesc(videoCategoryModel: VideoCategoryModel): List<VideoModel>?
	fun findAllByVideoCategoryOrderByFileDateAsc(videoCategoryModel: VideoCategoryModel): List<VideoModel>?
	fun findAllByFileStateAndVideoCategoryOrderByFileDateDesc(fileState: FileState, videoCategoryModel: VideoCategoryModel): List<VideoModel>?
	fun findAllByFileStateAndVideoCategoryOrderByFileDateAsc(fileState: FileState, videoCategoryModel: VideoCategoryModel): List<VideoModel>?
	fun findByThisIndex(thisIndex: Long): VideoModel?
	fun findByFileStateAndThisIndex(fileState: FileState, thisIndex: Long): VideoModel?
	fun findByFileName(fileName: String): VideoModel?
	fun findByFileStateAndFileName(fileState: FileState, fileName: String): VideoModel?
	fun findByFileNameAndSubPath(fileName: String, subPath: String?): VideoModel?
	fun findAllByFileNameContainingOrderByFileDateDesc(fileName: String): List<VideoModel>?
	fun findAllByFileNameContainingOrderByFileDateAsc(fileName: String): List<VideoModel>?
	fun findAllByFileStateAndFileNameContainingOrderByFileDateDesc(fileState: FileState, fileName: String): List<VideoModel>?
	fun findAllByFileStateAndFileNameContainingOrderByFileDateAsc(fileState: FileState, fileName: String): List<VideoModel>?
	fun findAllByTitleContainingOrderByFileDateDesc(title: String): List<VideoModel>?
	fun findAllByTitleContainingOrderByFileDateAsc(title: String): List<VideoModel>?
	fun findAllByFileStateAndTitleContainingOrderByFileDateDesc(fileState: FileState, title: String): List<VideoModel>?
	fun findAllByFileStateAndTitleContainingOrderByFileDateAsc(fileState: FileState, title: String): List<VideoModel>?
	fun findAllByHashTagContainingOrderByFileDateDesc(hashTag: String): List<VideoModel>?
	fun findAllByHashTagContainingOrderByFileDateAsc(hashTag: String): List<VideoModel>?
	fun findAllByFileStateAndHashTagContainingOrderByFileDateDesc(fileState: FileState, hashTag: String): List<VideoModel>?
	fun findAllByFileStateAndHashTagContainingOrderByFileDateAsc(fileState: FileState, hashTag: String): List<VideoModel>?
	fun findAllByFileNameContainingOrTitleContainingOrHashTagContainingOrderByFileDateDesc(fileName: String, title: String, hashTag: String): List<VideoModel>?
	fun findAllByFileNameContainingOrTitleContainingOrHashTagContainingOrderByFileDateAsc(fileName: String, title: String, hashTag: String): List<VideoModel>?
	fun findAllByFileStateAfterAndFileNameContainingOrTitleContainingOrHashTagContainingOrderByFileDateDesc(fileState: FileState, fileName: String, title: String, hashTag: String): List<VideoModel>?
	fun findAllByFileStateAfterAndFileNameContainingOrTitleContainingOrHashTagContainingOrderByFileDateAsc(fileState: FileState, fileName: String, title: String, hashTag: String): List<VideoModel>?
}