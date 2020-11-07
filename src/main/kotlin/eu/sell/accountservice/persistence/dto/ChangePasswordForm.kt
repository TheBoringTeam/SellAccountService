package eu.sell.accountservice.persistence.dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

class ChangePasswordForm @JsonCreator constructor(
    @JsonProperty("old_password")
    var oldPassword: String,

    @JsonProperty("new_password")
    var newPassword: String
)