package eu.sell.accountservice.services

import eu.sell.accountservice.persistence.dao.SellUser
import eu.sell.accountservice.persistence.dto.RegisterModel
import eu.sell.accountservice.repositories.IUserRepo
import eu.sell.accountservice.utls.exceptions.EntityNotFoundException
import eu.sell.accountservice.utls.exceptions.PasswordNotMatchException
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

    fun existsByUsername(username: String): Boolean {
        return userRepository.existsByUsername(username)
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

    fun updatePassword(userId: String, oldPassword: String, newPassword: String): SellUser {
        val user = findById(UUID.fromString(userId))
        if (isPasswordEquals(user, oldPassword)) {
            setPassword(user, newPassword)
        } else {
            log.error("The new password for user $userId is not the same with old one.")
            throw PasswordNotMatchException("Password for user $userId doesn't match")
        }
        saveAndFlushUser(user)
        return user
    }

    fun isPasswordEquals(user: SellUser, password: String): Boolean {
        return passwordEncoder.matches(password, user.password)
    }

    private fun setPassword(user: SellUser, newPassword: String) {
        user.password = passwordEncoder.encode(newPassword)
    }

    fun saveUser(user: SellUser): SellUser {
        return userRepository.save(user)
    }

    fun saveAndFlushUser(user: SellUser): SellUser {
        return userRepository.saveAndFlush(user)
    }

    fun registerUser(newUser: RegisterModel): SellUser {
        var sellUser =
            SellUser(newUser.username, newUser.publicName, passwordEncoder.encode(newUser.password), newUser.email)
        sellUser = saveAndFlushUser(sellUser)
        //TODO send registration email
        return sellUser
    }
}