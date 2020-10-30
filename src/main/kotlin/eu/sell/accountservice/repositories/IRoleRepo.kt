package eu.sell.accountservice.repositories

import eu.sell.accountservice.persistence.dao.SellRole
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface IRoleRepo : JpaRepository<SellRole, Int> {
}