package eu.sell.accountservice

import eu.sell.accountservice.repositories.IUserRepo
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit4.SpringRunner

@TestPropertySource("classpath:application-test.properties")
@SpringBootTest
@RunWith(SpringRunner::class)
class AccountServiceTests {

    @Autowired
    private lateinit var userRepo: IUserRepo

    @Before
    fun before() {
    }

    @Test
    fun contextLoads() {
    }
}