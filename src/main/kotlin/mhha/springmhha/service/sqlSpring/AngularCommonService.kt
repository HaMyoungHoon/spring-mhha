package mhha.springmhha.service.sqlSpring

import mhha.springmhha.advice.exception.AuthenticationEntryPointException
import mhha.springmhha.config.jpa.SpringJPAConfig
import mhha.springmhha.config.security.JwtTokenProvider
import mhha.springmhha.model.sqlASP.UserRole
import mhha.springmhha.model.sqlSpring.angular.doc.*
import mhha.springmhha.model.sqlSpring.angular.news.*
import mhha.springmhha.model.sqlSpring.angular.write.*
import mhha.springmhha.model.sqlSpring.common.LogModel
import mhha.springmhha.repository.sqlSpring.common.LogRepository
import mhha.springmhha.repository.sqlSpring.doc.*
import mhha.springmhha.repository.sqlSpring.news.*
import mhha.springmhha.repository.sqlSpring.write.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.sql.Timestamp

@Service
class AngularCommonService {
    @Autowired lateinit var logRepository: LogRepository
    @Autowired lateinit var newsRepository: NewsRepository
    @Autowired lateinit var docMenuRepository: DocMenuItemRepository
    @Autowired lateinit var writeDirectoryRepository: WriteDirectoryRepository
    @Autowired lateinit var writeFileRepository: WriteFileRepository
    @Autowired lateinit var jwtTokenProvider: JwtTokenProvider

    fun addLog(logModel: LogModel): LogModel {
        val minusTime = if (logModel.requestUri.startsWith("/v1/video/get/")) 3 * 60 * 1000
        else 1 * 30 * 1000
        val model = logRepository.findByLocalAddrAndRequestUriAndDateTimeGreaterThan(logModel.localAddr, logModel.requestUri, Timestamp(logModel.dateTime.time - minusTime))
        if (model != null) {
            return model
        }
        return logRepository.save(logModel)
    }
    fun getNewsAll() = newsRepository.findAllByOrderByThisIndexDesc()
    fun getNewsItem() = newsRepository.findFirstByOrderByThisIndexDesc()
    @Transactional(SpringJPAConfig.TRANSACTION_MANAGER)
    fun addNewsItem(token: String, data: NewsItem): NewsItem =
        isAdmin(token).let { newsRepository.save(data) }
    @Transactional(SpringJPAConfig.TRANSACTION_MANAGER)
    fun addNewsItem(token: String, data: List<NewsItem>): List<NewsItem> =
        isAdmin(token).let { newsRepository.saveAll(data) }

    fun getDocMenuAll(isDesc: Boolean = false) =
        if (isDesc) docMenuRepository.findAllByDocMenuItemOrderByThisIndexDesc(null)
        else docMenuRepository.findAllByDocMenuItemOrderByThisIndexAsc(null)
    fun getDocMenu(name: String) = docMenuRepository.findByName(name)
    fun getDocMenu(name: List<String>) = docMenuRepository.findAllByNameIn(name)
    @Transactional(SpringJPAConfig.TRANSACTION_MANAGER)
    fun addDocMenuItem(token: String, data: DocMenuItem): DocMenuItem =
        isAdmin(token).let { docMenuRepository.save(data) }
    @Transactional(SpringJPAConfig.TRANSACTION_MANAGER)
    fun addDocMenuItem(token: String, data: List<DocMenuItem>): List<DocMenuItem> =
        isAdmin(token).let { docMenuRepository.saveAll(data) }

    fun getWriteDirectoryAll(isDesc: Boolean = false) =
        if (isDesc) writeDirectoryRepository.findAllByWriteDirectoryOrderByThisIndexDesc(null)?.onEach { x -> x.initFile() }
        else writeDirectoryRepository.findAllByWriteDirectoryOrderByThisIndexAsc(null)?.onEach { x -> x.initFile() }
    fun getWriteDirectoryName(name: String) = writeDirectoryRepository.findByDirName(name)?.apply { initFile() }
    fun getWriteDirectoryNameWithFile(name: String) = writeDirectoryRepository.findByDirName(name)?.apply { setWriteFiles(this) }
    fun setWriteFiles(writeDirectory: WriteDirectory) {
        writeDirectory.writeFiles = writeFileRepository.findAllByWriteDirectory(writeDirectory).toMutableList()
        writeDirectory.children?.forEach { x -> setWriteFiles(x) }
    }
    @Transactional(SpringJPAConfig.TRANSACTION_MANAGER)
    fun addWriteDirectory(token: String, data: WriteDirectory): WriteDirectory =
        isAdmin(token).let { writeDirectoryRepository.save(data) }
    @Transactional(SpringJPAConfig.TRANSACTION_MANAGER)
    fun editWriteDirectory(token: String, data: WriteDirectory): WriteDirectory =
        isAdmin(token).let { writeDirectoryRepository.save(data) }

    fun getWriteFileAll(token: String): List<WriteFile>? =
        jwtTokenProvider.getUserData(token).let { x ->
            if (UserRole.fromFlag(x.role).contains(UserRole.Admin)) writeFileRepository.findAll()
            else writeFileRepository.findAllByAuthIndexOrderByThisIndexDesc(x.thisIndex)
        }
    fun getWriteFileName(name: String) = writeFileRepository.findByName(name)
    @Transactional(SpringJPAConfig.TRANSACTION_MANAGER)
    fun addWriteFile(token: String, data: WriteFile): WriteFile =
        isAdmin(token).let { _ ->
            data.authIndex = jwtTokenProvider.getUserData(token).thisIndex
            data.status = WriteFileStatus.None
            writeFileRepository.save(data)
        }
    @Transactional(SpringJPAConfig.TRANSACTION_MANAGER)
    fun editWriteFile(token: String, data: WriteFile): WriteFile =
        isAdmin(token, false).let { x ->
            if (!x) jwtTokenProvider.getUserData(token).let { y ->
                if (y.thisIndex != data.authIndex) throw AuthenticationEntryPointException() }
            writeFileRepository.save(data)
        }

    fun isAdmin(token: String?, notAdminThrow: Boolean = true): Boolean =
        token?.let { x ->
            val user = jwtTokenProvider.getUserData(x)
            if (UserRole.fromFlag(user.role).contains(UserRole.Admin)) true
            else if (notAdminThrow) throw AuthenticationEntryPointException()
            else false
        } ?: if (notAdminThrow) throw AuthenticationEntryPointException() else false
}