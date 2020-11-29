package eu.sell.accountservice.persistence.dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import eu.sell.accountservice.utls.validation.annotations.UniqueEmail
import eu.sell.accountservice.utls.validation.annotations.UniqueUsername
import javax.validation.constraints.*

class RegisterModel @JsonCreator constructor(
    @field:JsonProperty("username")
    @field:NotEmpty(message = "Username could not be empty")
    @field:NotNull(message = "Username could not be empty")
    @field:Size(message = "Login should be from 6 to 32 length", min = 5, max = 32)
    @field:Pattern(
        regexp = "^(?=[a-zA-Z0-9._])(?!.*[_.]{2})[^_.].*[^_.]\$",
        message = "Username format is not correct"
    )
    @UniqueUsername
    val username: String,

    @field:JsonProperty("public_name")
    @field:NotEmpty(message = "Public name could not be empty")
    @field:Size(message = "Public should be from 2 to 32 length", min = 2, max = 32)
    val publicName: String,

    @field:JsonProperty("password")
    @field:NotEmpty(message = "Password could not be empty")
    @field:Size(message = "Password should be from 6 to 32 length", min = 6, max = 32)
    var password: String,

    @field:JsonProperty("email")
    @field:NotEmpty(message = "Email could not be empty")
    @field:Email(message = "Email should be properly formatted")
    @UniqueEmail
    val email: String
)