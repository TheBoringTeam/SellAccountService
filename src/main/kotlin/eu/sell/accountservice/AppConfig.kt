package eu.sell.accountservice

import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class AppConfig {

    @Bean
    fun providePasswordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder(13)
    }
}