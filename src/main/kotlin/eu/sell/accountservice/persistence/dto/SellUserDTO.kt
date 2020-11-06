package eu.sell.accountservice.persistence.dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import eu.sell.accountservice.persistence.dao.SellUser
import java.util.*

class SellUserDTO {

    @JsonIgnore
    constructor(user: SellUser) {
        this.id = user.id
        this.username = user.username
        this.email = user.email
        this.publicName = user.publicName
        this.permissions = user.getPermissions()
    }

    @JsonCreator
    constructor(id: UUID, username: String, email: String, publicName: String, permissions: Set<String>) {
        this.id = id
        this.username = username
        this.email = email
        this.publicName = publicName
        this.permissions = permissions
    }

    @field:JsonProperty("id")
    var id: UUID

    @field:JsonProperty("username")
    var username: String

    @field:JsonProperty("email")
    var email: String

    @field:JsonProperty("public_name")
    var publicName: String

    @field:JsonProperty("permissions")
    var permissions: Set<String>

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SellUserDTO

        if (id != other.id) return false
        if (username != other.username) return false
        if (email != other.email) return false
        if (publicName != other.publicName) return false
        if (permissions != other.permissions) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + username.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + publicName.hashCode()
        result = 31 * result + permissions.hashCode()
        return result
    }
}