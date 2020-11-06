package eu.sell.accountservice.controllers

import eu.sell.accountservice.services.AccountService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

/**
 * Controller resposible for account information functionality
 */
@RestController
@RequestMapping("/accounts")
class AccountInfoController @Autowired constructor(private val accountService: AccountService) {

    private val log = LoggerFactory.getLogger(AccountInfoController::class.java)

    @GetMapping("/{userId}")
    fun findById(@PathVariable userId: String): ResponseEntity<*> {
        log.info("Searching for user with id: $userId")
        val userUUID = UUID.fromString(userId)
        return ResponseEntity.ok(accountService.findById(userUUID).getDTO())
    }

    @GetMapping("/username/{username}")
    fun findByUsername(@PathVariable username: String): ResponseEntity<*> {
        log.info("Searching for user with username: $username")
        return ResponseEntity.ok(accountService.findByUsername(username).getDTO())
    }

    @GetMapping("/email/{email}")
    fun findByEmail(@PathVariable("email") email: String): ResponseEntity<*> {
        log.info("Searching user with email: $email")
        return ResponseEntity.ok(accountService.findByEmail(email).getDTO())
    }
}