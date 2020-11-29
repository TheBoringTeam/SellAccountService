package eu.sell.accountservice.utls

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler


@ControllerAdvice
class CustomExceptionHandler : ResponseEntityExceptionHandler() {

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        val errors = hashMapOf<String, String>()
        for (error in ex.bindingResult.fieldErrors) {
            errors[error.field] = error.defaultMessage!!
        }
        for (error in ex.bindingResult.globalErrors) {
            errors[error.objectName] = error.defaultMessage!!
        }
        val apiError = ApiError(HttpStatus.BAD_REQUEST, "Arguments are not valid", errors)
        return handleExceptionInternal(ex, apiError, headers, apiError.status, request)
    }
}