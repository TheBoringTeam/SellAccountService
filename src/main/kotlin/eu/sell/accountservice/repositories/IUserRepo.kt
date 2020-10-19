package eu.sell.accountservice.repositories

import eu.sell.accountservice.persistence.dao.SellUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface IUserRepo : JpaRepository<SellUser, UUID> {
    fun findByEmail(email: String): Optional<SellUser>

    override fun findById(id: UUID): Optional<SellUser>

    fun existsByEmail(email: String): Boolean

    override fun existsById(id: UUID): Boolean
}