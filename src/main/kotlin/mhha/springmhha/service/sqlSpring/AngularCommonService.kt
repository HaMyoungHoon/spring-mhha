package mhha.springmhha.service.sqlSpring

import mhha.springmhha.advice.exception.AuthenticationEntryPointException
import mhha.springmhha.config.jpa.SpringJPAConfig
import mhha.springmhha.config.security.JwtTokenProvider
import mhha.springmhha.model.sqlASP.UserRole
import mhha.springmhha.model.sqlSpring.angular.doc.*
import mhha.springmhha.model.sqlSpring.angular.news.*
import mhha.springmhha.model.sqlSpring.angular.write.*
import mhha.springmhha.repository.sqlSpring.doc.*
import mhha.springmhha.repository.sqlSpring.news.*
import mhha.springmhha.repository.sqlSpring.write.*
import org.hibernate.Hibernate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AngularCommonService {
    @Autowired lateinit var newsRepository: NewsRepository
    @Autowired lateinit var docMenuRepository: DocMenuItemRepository
    @Autowired lateinit var writeDirectoryRepository: WriteDirectoryRepository
    @Autowired lateinit var writeFileRepository: WriteFileRepository
    @Autowired lateinit var jwtTokenProvider: JwtTokenProvider

    fun getNewsAll() = newsRepository.findAllByOrderByThisIndexDesc()
    fun getNewsItem() = newsRepository.findFirstByOrderByThisIndexDesc()
    @Transactional(SpringJPAConfig.TRANSACTION_MANAGER)
    fun addNewsItem(token: String, data: NewsItem): NewsItem {
        isAdmin(token)
        return newsRepository.save(data)
    }
    @Transactional(SpringJPAConfig.TRANSACTION_MANAGER)
    fun addNewsItem(token: String, data: List<NewsItem>): List<NewsItem> {
        isAdmin(token)
        return newsRepository.saveAll(data)
    }

    fun getDocMenuAll(isDesc: Boolean = false) = if (isDesc) {
        docMenuRepository.findAllByDocMenuItemOrderByThisIndexDesc(null)
    } else {
        docMenuRepository.findAllByDocMenuItemOrderByThisIndexAsc(null)
    }
    fun getDocMenu(name: String) = docMenuRepository.findByName(name)
    fun getDocMenu(name: List<String>) = docMenuRepository.findAllByNameIn(name)
    @Transactional(SpringJPAConfig.TRANSACTION_MANAGER)
    fun addDocMenuItem(token: String, data: DocMenuItem): DocMenuItem {
        isAdmin(token)
        return docMenuRepository.save(data)
    }
    @Transactional(SpringJPAConfig.TRANSACTION_MANAGER)
    fun addDocMenuItem(token: String, data: List<DocMenuItem>): List<DocMenuItem> {
        isAdmin(token)
        return docMenuRepository.saveAll(data)
    }

    fun getWriteDirectoryAll(isDesc: Boolean = false) = if (isDesc) {
        writeDirectoryRepository.findAllByWriteDirectoryOrderByThisIndexDesc(null)?.let { x ->
            x.forEach { y -> y.initFile() }
            x
        }
    } else {
        writeDirectoryRepository.findAllByWriteDirectoryOrderByThisIndexAsc(null)?.let { x ->
            x.forEach { y -> y.initFile() }
            x
        }
    }
    fun getWriteDirectoryName(name: String) = writeDirectoryRepository.findByDirName(name)?.let {
        it.initFile()
        it
    }
    fun getWriteDirectoryNameWithFile(name: String) = writeDirectoryRepository.findByDirName(name)?.let {
        setWriteFiles(it)
        it
    }
    fun setWriteFiles(writeDirectory: WriteDirectory) {
        writeDirectory.writeFiles = writeFileRepository.findAllByWriteDirectory(writeDirectory).toMutableList()
        writeDirectory.children?.forEach {
            setWriteFiles(it)
        }
    }
    @Transactional(SpringJPAConfig.TRANSACTION_MANAGER)
    fun addWriteDirectory(token: String, data: WriteDirectory): WriteDirectory {
        isAdmin(token)
        return writeDirectoryRepository.save(data)
    }
    @Transactional(SpringJPAConfig.TRANSACTION_MANAGER)
    fun editWriteDirectory(token: String, data: WriteDirectory): WriteDirectory {
        isAdmin(token)
        return writeDirectoryRepository.save(data)
    }

    fun getWriteFileAll(token: String): List<WriteFile>? {
        val user = jwtTokenProvider.getUserData(token)
        if (UserRole.fromFlag(user.role).contains(UserRole.Admin)) {
            return writeFileRepository.findAll()
        }

        return writeFileRepository.findAllByAuthIndexOrderByThisIndexDesc(user.thisIndex)
    }
    fun getWriteFileName(name: String) = writeFileRepository.findByName(name)
    @Transactional(SpringJPAConfig.TRANSACTION_MANAGER)
    fun addWriteFile(token: String, data: WriteFile): WriteFile {
        isAdmin(token)
        data.authIndex = jwtTokenProvider.getUserData(token).thisIndex
        data.status = WriteFileStatus.None
        return writeFileRepository.save(data)
    }
    @Transactional(SpringJPAConfig.TRANSACTION_MANAGER)
    fun editWriteFile(token: String, data: WriteFile): WriteFile {
        if (!isAdmin(token, false)) {
            val user = jwtTokenProvider.getUserData(token)
            if (user.thisIndex != data.authIndex) {
                throw AuthenticationEntryPointException()
            }
        }

        return writeFileRepository.save(data)
    }

    fun isAdmin(token: String, notAdminThrow: Boolean = true): Boolean {
        val user = jwtTokenProvider.getUserData(token)
        if (UserRole.fromFlag(user.role).contains(UserRole.Admin)) {
            return true
        }
        if (notAdminThrow) {
            throw AuthenticationEntryPointException()
        }

        return false
    }
}