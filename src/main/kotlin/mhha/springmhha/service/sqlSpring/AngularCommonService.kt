package mhha.springmhha.service.sqlSpring

import mhha.springmhha.config.security.JwtTokenProvider
import mhha.springmhha.model.sqlSpring.angular.doc.*
import mhha.springmhha.model.sqlSpring.angular.news.*
import mhha.springmhha.model.sqlSpring.angular.write.*
import mhha.springmhha.repository.sqlSpring.doc.*
import mhha.springmhha.repository.sqlSpring.news.*
import mhha.springmhha.repository.sqlSpring.write.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AngularCommonService {
    @Autowired lateinit var newsRepository: NewsRepository
    @Autowired lateinit var docMenuRepository: DocMenuItemRepository
    @Autowired lateinit var writeDirectoryRepository: WriteDirectoryRepository
    @Autowired lateinit var writeFileRepository: WriteFileRepository
    @Autowired lateinit var jwtTokenProvider: JwtTokenProvider

    fun getNewsAll() = newsRepository.findAllByOrderByThisIndexDesc()
    fun getNewsItem() = newsRepository.findFirstByOrderByThisIndexDesc()
    fun addNewsItem(data: NewsItem) = newsRepository.save(data)
    fun addNewsItem(data: List<NewsItem>) = newsRepository.saveAll(data)

    fun getDocMenuAll(isDesc: Boolean = false) = if (isDesc) {
        docMenuRepository.findAllByDocMenuItemOrderByThisIndexDesc(null)
    } else {
        docMenuRepository.findAllByDocMenuItemOrderByThisIndexAsc(null)
    }
    fun getDocMenu(name: String) = docMenuRepository.findByName(name)
    fun getDocMenu(name: List<String>) = docMenuRepository.findAllByNameIn(name)
    fun addDocMenuItem(data: DocMenuItem) = docMenuRepository.save(data)
    fun addDocMenuItem(data: List<DocMenuItem>) = docMenuRepository.saveAll(data)

    fun getWriteDirectoryAll(isDesc: Boolean = false) = if (isDesc) {
        writeDirectoryRepository.findAllByWriteDirectoryOrderByThisIndexDesc(null)
    } else {
        writeDirectoryRepository.findAllByWriteDirectoryOrderByThisIndexAsc(null)
    }

    fun isAdmin(token: String, notAdminThrow: Boolean = true): Boolean {
        val user = jwtTokenProvider.getUserData(token)

        return true
    }
}