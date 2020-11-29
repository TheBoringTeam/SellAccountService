package eu.sell.accountservice.controllers

import eu.sell.accountservice.persistence.dto.ChangePasswordForm
import eu.sell.accountservice.persistence.dto.RegisterModel
import eu.sell.accountservice.services.AccountService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

/**
 * Controller responsible for main authorization functionality
 */
@RestController
@RequestMapping("/accounts")
class AccountMainController @Autowired constructor(private val accountService: AccountService) {
    private val log = LoggerFactory.getLogger(AccountMainController::class.java)

    @PostMapping("/register")
    fun createUser(@Valid @RequestBody newUserModel: RegisterModel): ResponseEntity<*> {
        log.info("Creating a new user: $newUserModel")
        val user = accountService.registerUser(newUserModel).getDTO()
        return ResponseEntity.ok(user)
    }

    @PostMapping("/login")
    fun authorizeUser(): ResponseEntity<*> {
        TODO("Implement login functionality")
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