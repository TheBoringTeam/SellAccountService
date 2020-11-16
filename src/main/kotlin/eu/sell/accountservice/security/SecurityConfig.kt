package eu.sell.accountservice.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class SecurityConfig {

    @Bean
    fun providePasswordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder(13)
    }
}