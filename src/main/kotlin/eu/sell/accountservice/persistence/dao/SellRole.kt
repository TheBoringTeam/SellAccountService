package eu.sell.accountservice.persistence.dao

import javax.persistence.*

@Entity(name = "roles")
class SellRole(
        @Column(name = "role_name")
        var name: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_id")
    var id: Int = 0

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    var rolePermissions: Set<RolePermission> = setOf()
}