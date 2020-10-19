package eu.sell.accountservice.persistence.dto

import eu.sell.accountservice.persistence.dao.SellUser

class SellUserDTO constructor(user: SellUser) {
    var username = user.username

    var email = user.email

    var id = user.id

    var publicName = user.publicName

    var permissions = user.getPermissions()
}