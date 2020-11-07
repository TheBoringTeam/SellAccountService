package eu.sell.accountservice.utls.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
class PasswordNotMatchException(message: String) : Exception(message)