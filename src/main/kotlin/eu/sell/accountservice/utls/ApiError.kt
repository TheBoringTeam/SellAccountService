package eu.sell.accountservice.utls

import org.springframework.http.HttpStatus

class ApiError {
    var status: HttpStatus
    var message: String
    var errors: Map<String, String>

    constructor(status: HttpStatus, message: String, errors: Map<String, String>) : super() {
        this.status = status
        this.message = message
        this.errors = errors
    }
}