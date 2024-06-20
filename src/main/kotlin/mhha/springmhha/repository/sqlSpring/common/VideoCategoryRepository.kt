package mhha.springmhha.repository.sqlSpring.common

import mhha.springmhha.model.sqlSpring.common.VideoCategoryModel
import mhha.springmhha.model.sqlSpring.common.VideoCategoryState
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface VideoCategoryRepository: JpaRepository<VideoCategoryModel, Long> {
	fun findAllByVideoCategoryOrderByDirNameDesc(videoCategory: VideoCategoryModel?): List<VideoCategoryModel>?
	fun findAllByVideoCategoryOrderByDirNameAsc(videoCategory: VideoCategoryModel?): List<VideoCategoryModel>?
	fun findAllByVideoCategoryAndVideoCategoryStateNotOrderByDirNameDesc(videoCategory: VideoCategoryModel?, videoCategoryState: VideoCategoryState): List<VideoCategoryModel>?
	fun findAllByVideoCategoryAndVideoCategoryStateNotOrderByDirNameAsc(videoCategory: VideoCategoryModel?, videoCategoryState: VideoCategoryState): List<VideoCategoryModel>?
	fun findByThisIndex(thisIndex: Long): VideoCategoryModel?
	fun findByDirName(dirName: String): VideoCategoryModel?
	fun findByVideoCategoryStateAndThisIndex(videoCategoryState: VideoCategoryState, thisIndex: Long): VideoCategoryModel?
	fun findByVideoCategoryStateAndDirName(videoCategoryState: VideoCategoryState, dirName: String): VideoCategoryModel?

	@Query("SELECT * FROM VideoCategoryModel WHERE dirName = :dirName AND videoCategory_thisIndex IS NULL ORDER BY dirName", nativeQuery = true)
	fun findRootDirName(dirName: String): VideoCategoryModel?
	@Query("SELECT * FROM VideoCategoryModel WHERE videoCategory_thisIndex IS NULL ORDER BY dirName", nativeQuery = true)
	fun findRoot(): List<VideoCategoryModel>?
	@Query("SELECT * FROM VideoCategoryModel WHERE videoCategoryState = 0 AND videoCategory_thisIndex IS NULL ORDER BY dirName", nativeQuery = true)
	fun findRootStateOK(): List<VideoCategoryModel>?
}