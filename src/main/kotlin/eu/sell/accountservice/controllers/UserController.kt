package eu.sell.accountservice.controllers

import eu.sell.accountservice.persistence.dto.NewUserDTO
import eu.sell.accountservice.services.UserService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/user")
class UserController @Autowired constructor(private val userService: UserService) {

    private val log = LoggerFactory.getLogger(UserController::class.java)

    @GetMapping("/{userId}")
    fun getUserInformation(@PathVariable userId: String): ResponseEntity<*> {
        val userUUID = UUID.fromString(userId)
        return ResponseEntity.ok(userService.findById(userUUID).getDTO())
    }

    @PostMapping("/register")
    fun registerUser(@RequestBody userDTO: NewUserDTO) {

    }
}