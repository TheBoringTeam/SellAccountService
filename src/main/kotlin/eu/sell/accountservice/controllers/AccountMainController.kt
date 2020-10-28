package eu.sell.accountservice.controllers

import eu.sell.accountservice.persistence.dto.NewUserDTO
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/accounts")
class AccountMainController {
    private val log = LoggerFactory.getLogger(AccountMainController::class.java)

    @PostMapping("/register")
    fun registerUser(@RequestBody userDTO: NewUserDTO): ResponseEntity<*> {
        TODO("Implement creating a new user endpoint")
    }
}