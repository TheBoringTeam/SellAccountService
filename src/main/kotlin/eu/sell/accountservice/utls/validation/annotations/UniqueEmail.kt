package eu.sell.accountservice.utls.validation.annotations

import eu.sell.accountservice.utls.validation.UniqueEmailValidator
import javax.validation.Constraint
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [UniqueEmailValidator::class])
annotation class UniqueEmail(
    val message: String = "There is already user with this email!",
    val groups: Array<KClass<out Any>> = [],
    val payload: Array<KClass<out Any>> = []
)
