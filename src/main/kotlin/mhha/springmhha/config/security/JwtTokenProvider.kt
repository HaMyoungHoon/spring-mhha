package mhha.springmhha.config.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import jakarta.annotation.PostConstruct
import jakarta.servlet.http.HttpServletRequest
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

@Component
class JwtTokenProvider {
    @Value(value = "\${spring.jwt.secret}")
    var secretKey: String = ""
    var tokenValidMS = 1000L * 60 * 60 * 8
    @Autowired
    lateinit var userDetailService: UserDetailsService
    companion object {
        const val authToken: String = "token"
    }
    @PostConstruct
    protected fun init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.toByteArray())
    }

    fun getSignKey(): Key = Keys.hmacShaKeyFor(secretKey.toByteArray())
    fun getSecretKey(): SecretKey = Keys.hmacShaKeyFor(secretKey.toByteArray())
    fun createToken(user: UserData, validTime: Long = tokenValidMS): String {
        val now = Date()
        return Jwts.builder().claims(Jwts.claims().subject(user.id).apply {
            this.add("id", user.id)
            this.add("name", user.name)
            this.add("role", user.role)
            this.add("status", user.status)
        }.build()).issuedAt(now).expiration(Date(now.time + validTime)).signWith(getSignKey()) .compact()
    }
    fun getAllClaimsFromToken(token : String): Claims = Jwts.parser().verifyWith(getSecretKey()).build().parseSignedClaims(token).payload
    fun getAuthentication(token : String): Authentication {
        val userDetails = userDetailService.loadUserByUsername(getUserPk(token))
        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }
    fun getUserPk(token : String): String = getAllClaimsFromToken(token).subject
    fun resolveToken(req : HttpServletRequest): String? =
            req.getHeader(authToken)
    fun validateToken(token : String) = try {
        !getAllClaimsFromToken(token).expiration.before(Date())
    }
    catch (e: Exception) {
        false
    }
}