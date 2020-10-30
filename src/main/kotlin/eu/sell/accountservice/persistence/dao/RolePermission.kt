package eu.sell.accountservice.persistence.dao

import javax.persistence.*

@Entity(name = "role_permissions")
class RolePermission(
        @ManyToOne
        @JoinColumn(name = "role_id")
        var role: SellRole,

        @ManyToOne
        @JoinColumn(name = "permission_id")
        var permission: SellPermission
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_permission_id")
    var id: Int = 0
}