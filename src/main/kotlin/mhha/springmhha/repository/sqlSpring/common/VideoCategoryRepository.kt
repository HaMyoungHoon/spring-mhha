package mhha.springmhha.repository.sqlSpring.common

import mhha.springmhha.model.sqlSpring.common.VideoCategoryModel
import mhha.springmhha.model.sqlSpring.common.VideoCategoryState
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface VideoCategoryRepository: JpaRepository<VideoCategoryModel, Long> {
	fun findAllByVideoCategoryOrderByThisIndexDesc(videoCategory: VideoCategoryModel?): List<VideoCategoryModel>?
	fun findAllByVideoCategoryOrderByThisIndexAsc(videoCategory: VideoCategoryModel?): List<VideoCategoryModel>?
	fun findAllByVideoCategoryAndVideoCategoryStateNotOrderByThisIndexDesc(videoCategory: VideoCategoryModel?, videoCategoryState: VideoCategoryState): List<VideoCategoryModel>?
	fun findAllByVideoCategoryAndVideoCategoryStateNotOrderByThisIndexAsc(videoCategory: VideoCategoryModel?, videoCategoryState: VideoCategoryState): List<VideoCategoryModel>?
	fun findByThisIndex(thisIndex: Long): VideoCategoryModel?
	fun findByDirName(dirName: String): VideoCategoryModel?
	fun findByVideoCategoryStateAndDirName(videoCategoryState: VideoCategoryState, dirName: String): VideoCategoryModel?

	@Query("SELECT * FROM VideoCategoryModel WHERE dirName = :dirName AND videoCategory_thisIndex IS NULL", nativeQuery = true)
	fun findRootDirName(dirName: String): VideoCategoryModel?
}