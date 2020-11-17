package eu.sell.accountservice.persistence.dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

class NewUserDTO @JsonCreator constructor(
    @field:JsonProperty("username")
    val username: String,
    @field:JsonProperty("public_name")
    val publicName: String,
    @field:JsonProperty("password")
    var password: String,
    @field:JsonProperty("email")
    val email: String
) {
}