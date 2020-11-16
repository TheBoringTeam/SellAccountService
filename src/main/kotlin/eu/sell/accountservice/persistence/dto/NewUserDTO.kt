package eu.sell.accountservice.persistence.dto

class NewUserDTO(
    val username: String,
    val publicName: String,
    var password: String,
    val email: String
) {
}