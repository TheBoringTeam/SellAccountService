package eu.sell.accountservice.security.filters

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import eu.sell.accountservice.persistence.dto.LoginModel
import eu.sell.accountservice.services.AccountService
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.slf4j.LoggerFactory
import org.springframework.core.env.Environment
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.util.stream.Collectors
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthFilter constructor(
    private val accountService: AccountService,
    private val authManager: AuthenticationManager,
    private val env: Environment
) :
    UsernamePasswordAuthenticationFilter() {

    private val log = LoggerFactory.getLogger(AuthFilter::class.java)

    private val objectMapper = jacksonObjectMapper()

    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication? {
        log.info("Trying to authenticate... ${request.reader}")

        val jsonResponse = request.reader.lines().collect(Collectors.joining())

        log.info("Trying to authenticate... $jsonResponse")

        val auth: LoginModel = objectMapper.readValue(jsonResponse)

        return authManager.authenticate(
            UsernamePasswordAuthenticationToken(
                auth.username,
                auth.password,
                listOf()
            )
        )
    }

    override fun successfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain,
        authResult: Authentication
    ) {
        val username = (authResult.principal as User).username
        val user = accountService.findByUsername(username)
        // use it in case when we gonna use email as the first login param
//        val user = userDetailsService.loadUserByUsername(username)

        //Can use setExpiration date if we need it
        val token = Jwts.builder()
            .setSubject(user.id.toString())
            .signWith(SignatureAlgorithm.HS256, env.getProperty("auth.secret_key"))
            .compact()
        response.addHeader("token", token)
        response.addHeader("userId", user.id.toString())
    }

//    override fun successfulAuthentication(
//        request: HttpServletRequest,
//        response: HttpServletResponse,
//        authResult: Authentication
//    ) {
////        val username = (authResult.principal as User).username
////        val user = accountService.findByUsername(username)
////        // use it in case when we gonna use email as the first login param
//////        val user = userDetailsService.loadUserByUsername(username)
////
////        //Can use setExpiration date if we need it
////        val token = Jwts.builder()
////            .setSubject(user.id.toString())
////            .signWith(SignatureAlgorithm.HS256, secretKey)
////            .compact()
////        response.addHeader("token", token)
////        response.addHeader("userId", user.id.toString())
//    }
}