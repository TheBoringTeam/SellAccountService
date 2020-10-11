package eu.sell.accountservice.persistence.dao

import javax.persistence.*

@Entity(name = "permissions")
class SellPermission(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Int,

        @Column(name = "permission_name")
        var name: String
) {
}