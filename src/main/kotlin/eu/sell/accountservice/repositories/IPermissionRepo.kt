package eu.sell.accountservice.repositories

import eu.sell.accountservice.persistence.dao.SellPermission
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface IPermissionRepo : JpaRepository<SellPermission, Int> {
}