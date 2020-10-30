package eu.sell.accountservice.persistence.dto

import eu.sell.accountservice.persistence.dao.SellUser

class NewUserDTO(
    val username: String,
    val publicName: String,
    var password: String,
    val email: String
) {
    fun getSellUser() = SellUser(username, publicName, password, email)
}