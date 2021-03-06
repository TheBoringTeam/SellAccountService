package eu.sell.accountservice.controllers

import eu.sell.accountservice.persistence.dto.ChangePasswordForm
import eu.sell.accountservice.persistence.dto.NewUserDTO
import eu.sell.accountservice.services.AccountService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * Controller responsible for main authorization functionality
 */
@RestController
@RequestMapping("/accounts")
class AccountMainController @Autowired constructor(private val accountService: AccountService) {
    private val log = LoggerFactory.getLogger(AccountMainController::class.java)

    @PostMapping("/create")
    fun createUser(@RequestBody userDTO: NewUserDTO): ResponseEntity<*> {
        log.info("Creating a new user: $userDTO")
        val user = accountService.registerUser(userDTO).getDTO()
        return ResponseEntity.ok(user)
    }

    @PutMapping("/update/password/{userId}")
    fun updatePassword(
        @PathVariable("userId") userId: String,
        @RequestBody changePasswordForm: ChangePasswordForm
    ): ResponseEntity<*> {
        log.info("Updating password for user $userId")
        val user = accountService.updatePassword(userId, changePasswordForm.oldPassword, changePasswordForm.newPassword)
        return ResponseEntity.ok(user.getDTO())
    }
}