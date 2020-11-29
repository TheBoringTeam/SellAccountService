package eu.sell.accountservice.utls.validation

import eu.sell.accountservice.services.AccountService
import eu.sell.accountservice.utls.exceptions.WrongArgumentsException
import eu.sell.accountservice.utls.validation.annotations.UniqueEmail
import org.springframework.beans.factory.annotation.Autowired
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class UniqueEmailValidator : ConstraintValidator<UniqueEmail, String> {

    @Autowired
    lateinit var accountService: AccountService

    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean {
        if (value == null) {
            throw WrongArgumentsException("Email could not be empty")
        } else {
            return !accountService.existsByEmail(value)
        }
    }
}