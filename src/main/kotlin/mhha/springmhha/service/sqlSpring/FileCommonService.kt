package mhha.springmhha.service.sqlSpring

import mhha.springmhha.advice.exception.AuthenticationEntryPointException
import mhha.springmhha.advice.exception.NotValidOperationException
import mhha.springmhha.config.jpa.SpringJPAConfig
import mhha.springmhha.config.security.JwtTokenProvider
import mhha.springmhha.model.common.Storage
import mhha.springmhha.model.sqlASP.UserRole
import mhha.springmhha.model.sqlSpring.common.FileModel
import mhha.springmhha.repository.sqlSpring.common.FileRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class FileCommonService {
	@Autowired lateinit var fileRepository: FileRepository
	@Autowired lateinit var jwtTokenProvider: JwtTokenProvider

	fun getFileModel(startCount: Int? = 0, endCount: Int? = 10, fileType: Storage) = fileRepository.findStartEndCount(startCount ?: 0, endCount ?: 10, fileType)
	fun getFileModel(thisIndex: Long) = fileRepository.findByThisIndex(thisIndex)
	@Transactional(SpringJPAConfig.TRANSACTION_MANAGER)
	fun addFileModel(token: String, fileModel: FileModel): FileModel {
		isAdmin(token)
		val buff = fileRepository.findByFileNameAndFileExtAndFileType(fileModel.fileName, fileModel.fileExt, fileModel.fileType)
		if (buff != null) {
			throw NotValidOperationException()
		}
		return fileRepository.save(FileModel(0, fileModel.fileName, fileModel.fileExt, fileModel.fileType))
	}
	@Transactional(SpringJPAConfig.TRANSACTION_MANAGER)
	fun addFileModel(token: String, fileName: String, fileExt: String, fileType: Storage): FileModel {
		isAdmin(token)
		val buff = fileRepository.findByFileNameAndFileExtAndFileType(fileName, fileExt, fileType)
		if (buff != null) {
			throw NotValidOperationException()
		}
		return fileRepository.save(FileModel(0, fileName, fileExt, fileType))
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