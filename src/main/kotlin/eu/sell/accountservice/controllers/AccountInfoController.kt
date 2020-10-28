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

@RestController
@RequestMapping("/accounts")
class AccountInfoController @Autowired constructor(private val accountService: AccountService) {

    private val log = LoggerFactory.getLogger(AccountInfoController::class.java)

    @GetMapping("/{userId}")
    fun findById(@PathVariable userId: String): ResponseEntity<*> {
        val userUUID = UUID.fromString(userId)
        return ResponseEntity.ok(accountService.findById(userUUID).getDTO())
    }

    @GetMapping("/email/{accountEmail}")
    fun findByEmail(@PathVariable("accountEmail") email: String): ResponseEntity<*> {
        log.info("Searching user with email: $email")
        return ResponseEntity.ok(accountService.findByEmail(email).getDTO())
    }
}