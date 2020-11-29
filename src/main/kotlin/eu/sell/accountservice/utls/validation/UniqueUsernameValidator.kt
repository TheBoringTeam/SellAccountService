package eu.sell.accountservice.utls.validation

import eu.sell.accountservice.services.AccountService
import eu.sell.accountservice.utls.exceptions.WrongArgumentsException
import eu.sell.accountservice.utls.validation.annotations.UniqueUsername
import org.springframework.beans.factory.annotation.Autowired
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class UniqueUsernameValidator : ConstraintValidator<UniqueUsername, String> {

    @Autowired
    lateinit var accountService: AccountService

    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean {
        if (value == null) {
            throw WrongArgumentsException("Username could not be empty")
        } else {
            return !accountService.existsByUsername(value)
        }
    }
}