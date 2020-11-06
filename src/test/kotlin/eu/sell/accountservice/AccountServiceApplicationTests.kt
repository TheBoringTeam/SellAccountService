package eu.sell.accountservice

import eu.sell.accountservice.repositories.IUserRepo
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit4.SpringRunner

@SpringBootTest
@RunWith(SpringRunner::class)
@TestPropertySource("classpath:application-test.properties")
class AccountServiceApplicationTests {

    @Autowired
    lateinit var userRepo: IUserRepo

    @Test
    fun contextLoads() {
    }
}
