package eu.sell.accountservice.persistence.dao

import java.time.LocalDateTime
import javax.persistence.*

@Entity(name = "user_roles")
class UserRole(
        @ManyToOne
        @JoinColumn(name = "user_id")
        var user: SellUser,

        @ManyToOne
        @JoinColumn(name = "role_id")
        var role: SellRole
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_role_id")
    var id: Int = 0

    @Column(name = "expires_at")
    var expiresAt: LocalDateTime = LocalDateTime.MAX
}