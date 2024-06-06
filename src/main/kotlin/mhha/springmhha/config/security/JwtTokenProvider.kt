package mhha.springmhha.config.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import jakarta.annotation.PostConstruct
import jakarta.servlet.http.HttpServletRequest
import mhha.springmhha.config.FConstants
import mhha.springmhha.model.common.CustomUserModel
import mhha.springmhha.model.sqlASP.UserData
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import java.lang.Exception
import java.security.Key
import java.util.*
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

@Component
class JwtTokenProvider {
    @Value(value = "\${spring.jwt.secret}")
    var secretKey: String = ""
    var tokenValidMS = 1000L * 60 * 60 * 24
    @Autowired
    lateinit var userDetailService: UserDetailsService
    companion object {
        const val authToken: String = "token"
    }
    @PostConstruct
    protected fun init() {
//        secretKey = Base64.getEncoder().encodeToString(secretKey.toByteArray())
    }

    fun getSignKey(): Key = SecretKeySpec(secretKey.toByteArray(), "HmacSHA256")
    fun getSecretKey(): SecretKey = SecretKeySpec(secretKey.toByteArray(), "HmacSHA256")
    fun createToken(user: UserData, validTime: Long = tokenValidMS): String {
        val now = Date()
        return Jwts.builder().claims(Jwts.claims().subject(user.id).apply {
            this.add(FConstants.CLAIM_ID, user.id)
            this.add(FConstants.CLAIM_NAME, user.name)
            this.add(FConstants.CLAIM_ROLE, user.role.toString())
            this.add(FConstants.CLAIM_STATUS, user.status)
        }.build()).expiration(Date(now.time + validTime)).signWith(getSignKey()).compact()
    }
    fun getAllClaimsFromToken(token : String): Claims = Jwts.parser().verifyWith(getSecretKey()).build().parseSignedClaims(token).payload
    fun getAuthentication(token : String): Authentication {
        val userDetails = userDetailService.loadUserByUsername(getUserPk(token))
        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }
    fun getUserPk(token : String): String = getAllClaimsFromToken(token).subject
    fun getUserData(token: String) = UserData().apply {
        setData(getAuthentication(token).principal as CustomUserModel)
    }
    fun resolveToken(req : HttpServletRequest): String? =
            req.getHeader(authToken)
    fun validateToken(token : String) = try {
        !getAllClaimsFromToken(token).expiration.before(Date())
    }
    catch (e: Exception) {
        false
    }
}