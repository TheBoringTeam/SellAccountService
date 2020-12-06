package eu.sell.accountservice.persistence.dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.NotEmpty

class LoginModel @JsonCreator constructor(
    @field:JsonProperty("username")
    @field:NotEmpty(message = "Email could not be empty")
    var username: String,

    @field:NotEmpty(message = "Password could not be empty")
    @field:JsonProperty("password")
    var password: String
)