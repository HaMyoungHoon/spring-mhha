package mhha.springmhha.repository.sqlSpring.news

import mhha.springmhha.model.sqlSpring.angular.news.NewsItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface NewsRepository : JpaRepository<NewsItem, Long> {
	fun findAllByOrderByThisIndexDesc(): List<NewsItem>?
	fun findFirstByOrderByThisIndexDesc(): NewsItem?
}