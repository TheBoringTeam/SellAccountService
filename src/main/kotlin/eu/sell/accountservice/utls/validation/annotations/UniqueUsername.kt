package eu.sell.accountservice.utls.validation.annotations

import eu.sell.accountservice.utls.validation.UniqueEmailValidator
import eu.sell.accountservice.utls.validation.UniqueUsernameValidator
import javax.validation.Constraint
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [UniqueUsernameValidator::class])
annotation class UniqueUsername(
    val message: String = "There is already user with this username!",
    val groups: Array<KClass<out Any>> = [],
    val payload: Array<KClass<out Any>> = []
)
