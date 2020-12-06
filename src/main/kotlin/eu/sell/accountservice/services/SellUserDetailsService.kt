package eu.sell.accountservice.services

import eu.sell.accountservice.repositories.IUserRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class SellUserDetailsService @Autowired constructor(private val userRepo: IUserRepo) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val userDb = userRepo.findByUsername(username)
        val user = userDb.orElseThrow { throw UsernameNotFoundException("User with email: $username is not found") }
        // edit here for "lock" functionality
        return User(user.username, user.password, user.isActivated, true, true, true, user.getPermissions())
    }
}