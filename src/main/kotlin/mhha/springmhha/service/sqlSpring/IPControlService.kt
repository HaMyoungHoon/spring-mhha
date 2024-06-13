package mhha.springmhha.service.sqlSpring

import mhha.springmhha.advice.exception.AuthenticationEntryPointException
import mhha.springmhha.config.jpa.SpringJPAConfig
import mhha.springmhha.config.security.JwtTokenProvider
import mhha.springmhha.model.sqlASP.UserRole
import mhha.springmhha.model.sqlSpring.common.IPBlockModel
import mhha.springmhha.repository.sqlSpring.common.IPBlockRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class IPControlService {
	@Autowired lateinit var ipBlockRepository: IPBlockRepository
	@Autowired lateinit var jwtTokenProvider: JwtTokenProvider

	fun getIPBlockModel() = ipBlockRepository.findAll()
	fun getIPBlockModel(ipAddr: String) = ipBlockRepository.findByIpAddr(ipAddr)
	@Transactional(SpringJPAConfig.TRANSACTION_MANAGER)
	fun addIPBlockModelForController(token: String, ipBlockModel: IPBlockModel): IPBlockModel {
		isAdmin(token)
		if (getIPBlockModel(ipBlockModel.ipAddr) != null) {
			return ipBlockModel
		}

		return ipBlockRepository.save(ipBlockModel)
	}
	@Transactional(SpringJPAConfig.TRANSACTION_MANAGER)
	fun addIPBlockModel(ipBlockModel: IPBlockModel): IPBlockModel {
		if (getIPBlockModel(ipBlockModel.ipAddr) != null) {
			return ipBlockModel
		}

		return ipBlockRepository.save(ipBlockModel)
	}
	@Transactional(SpringJPAConfig.TRANSACTION_MANAGER)
	fun setIPBlockModel(ipAddr: String, block: Boolean): IPBlockModel {
		val model = getIPBlockModel(ipAddr) ?: return IPBlockModel()
		model.isBlock = block
		return ipBlockRepository.save(model)
	}
	@Transactional(SpringJPAConfig.TRANSACTION_MANAGER)
	fun setIPBlockModelForController(token: String, ipAddr: String, block: Boolean): IPBlockModel {
		isAdmin(token)
		val model = getIPBlockModel(ipAddr) ?: return IPBlockModel()
		model.isBlock = block
		return ipBlockRepository.save(model)
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