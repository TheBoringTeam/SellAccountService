package eu.sell.accountservice.controllers

import eu.sell.accountservice.persistence.dto.NewUserDTO
import eu.sell.accountservice.services.AccountService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Controller responsible for main authorization functionality
 */
@RestController
@RequestMapping("/accounts")
class AccountMainController @Autowired constructor(private val accountService: AccountService) {
    private val log = LoggerFactory.getLogger(AccountMainController::class.java)

    @PostMapping("/create")
    fun registerUser(@RequestBody userDTO: NewUserDTO): ResponseEntity<*> {
        log.info("Creating a new user: $userDTO")
        val user = accountService.registerUser(userDTO).getDTO()
        return ResponseEntity.ok(user)
    }
}