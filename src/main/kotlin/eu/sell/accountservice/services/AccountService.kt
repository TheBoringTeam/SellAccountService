package eu.sell.accountservice.services

import eu.sell.accountservice.persistence.dao.SellUser
import eu.sell.accountservice.persistence.dto.NewUserDTO
import eu.sell.accountservice.repositories.IUserRepo
import eu.sell.accountservice.utls.exceptions.EntityNotFoundException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Lazy
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class AccountService @Autowired constructor(
    private val userRepository: IUserRepo,
    @Lazy
    private val passwordEncoder: PasswordEncoder
) {
    private val log = LoggerFactory.getLogger(AccountService::class.java)

    fun existsByEmail(email: String): Boolean {
        return userRepository.existsByEmail(email)
    }

    fun findById(userId: UUID): SellUser {
        return userRepository.findById(userId)
            .orElseThrow { throw EntityNotFoundException("User was not found with provided id: $userId") }
    }

    fun findByEmail(userEmail: String): SellUser {
        return userRepository.findByEmail(userEmail)
            .orElseThrow { throw EntityNotFoundException("User was not found with provided email: $userEmail") }
    }

    fun findByUsername(username: String): SellUser {
        return userRepository.findByUsername(username)
            .orElseThrow { throw EntityNotFoundException("User was not found with provided username: $username") }
    }

    fun isPasswordEquals(user: SellUser, password: String): Boolean {
        return passwordEncoder.matches(password, user.password)
    }

    fun setPassword(user: SellUser, newPassword: String) {
        user.password = passwordEncoder.encode(newPassword)
    }

    fun saveUser(user: SellUser) {
        userRepository.save(user)
    }

    fun saveAndFlushUser(user: SellUser): SellUser {
        return userRepository.saveAndFlush(user)
    }

    fun registerUser(newUser: NewUserDTO): SellUser {
        val sellUser = newUser.getSellUser()
        userRepository.saveAndFlush(sellUser)
        //TODO send registration email
        return sellUser
    }
}