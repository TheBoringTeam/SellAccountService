package eu.sell.accountservice.persistence.dao

import eu.sell.accountservice.persistence.dto.SellUserDTO
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity(name = "sell_user")
class SellUser(
        @Column(name = "username", nullable = false, unique = true)
        var username: String,

        @Column(name = "public_name", nullable = false)
        var publicName: String,

        @Column(name = "password", nullable = false)
        var password: String,

        @Column(name = "email", nullable = false, unique = true)
        var email: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    var id: UUID = UUID.randomUUID()

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    var userRoles: Set<UserRole> = setOf()

    fun getPermissions(): Set<String> {
        val permissions = hashSetOf<String>()
        userRoles.filter { it.expiresAt.isAfter(LocalDateTime.now()) }
                .forEach {
                    it.role.rolePermissions.forEach { rolePermission -> permissions.add(rolePermission.permission.name) }
                }
        return permissions
    }

    fun getDTO() = SellUserDTO(this)
}