package eu.sell.accountservice.persistence.dao

import com.fasterxml.jackson.annotation.JsonIgnore
import eu.sell.accountservice.persistence.dto.SellUserDTO
import org.springframework.security.core.authority.SimpleGrantedAuthority
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
    var email: String,

    @Column(name = "is_activated", nullable = false)
    var isActivated: Boolean
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    var id: UUID = UUID.randomUUID()

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    var userRoles: Set<UserRole> = setOf()

    fun getPermissions(): List<SimpleGrantedAuthority> {
        val permissions = arrayListOf<SimpleGrantedAuthority>()
        userRoles.filter { it.expiresAt.isAfter(LocalDateTime.now()) }
            .forEach {
                it.role.rolePermissions.forEach { rolePermission ->
                    permissions.add(
                        SimpleGrantedAuthority(
                            rolePermission.permission.name
                        )
                    )
                }
            }
        return permissions
    }

    /**
     * Returns DTO object for user entity for purpose of hinding the password
     */
    @JsonIgnore
    fun getDTO() = SellUserDTO(this)
}