package eu.sell.accountservice.security

import eu.sell.accountservice.security.filters.AuthFilter
import eu.sell.accountservice.services.AccountService
import eu.sell.accountservice.services.SellUserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
@EnableWebSecurity
class SecurityConfig @Autowired constructor(
    private val userDetailsService: SellUserDetailsService,
    private val passwordEncoder: PasswordEncoder,
    private val accountService: AccountService,
    private val env: Environment
) :
    WebSecurityConfigurerAdapter() {

    @Value("\${auth.gateway-ip}")
    lateinit var gatewayIp: String

    override fun configure(http: HttpSecurity) {
        http.csrf().disable()
//        http.authorizeRequests()
//            .antMatchers("/**").hasIpAddress(gatewayIp)
////            .antMatchers("/accounts/register").permitAll()
//            .and()
//            .addFilter(getAuthFilter())
        http.addFilter(getAuthFilter())
        http.headers().frameOptions().disable()
    }

    fun getAuthFilter(): AuthFilter {
        val filter = AuthFilter(accountService, authenticationManager(), env)
//        filter.setAuthenticationManager(authenticationManager())
        filter.setFilterProcessesUrl("/login")
//        filter.filterProcessesUrl
        return filter
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder)
    }
}