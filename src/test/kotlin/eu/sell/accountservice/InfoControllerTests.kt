package eu.sell.accountservice

import eu.sell.accountservice.persistence.dao.SellUser
import eu.sell.accountservice.persistence.dto.SellUserDTO
import eu.sell.accountservice.repositories.IUserRepo
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit4.SpringRunner

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner::class)
@TestPropertySource("classpath:application-test.properties")
class InfoControllerTests {

    @Autowired
    private lateinit var userRepo: IUserRepo

    @LocalServerPort
    private var port: Int = 0

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Before
    fun prepareTests() {
        userRepo.deleteAll()
    }

    @Test
    fun contextLoads() {
    }

    @Test
    fun `show user information`() {
        // create user in db
        val user = userRepo.save(SellUser("testUsername", "pName", "pass", "test@mail.com"))

        val request = HttpEntity(user.id.toString())

        val response =
            restTemplate.exchange(
                "http://localhost:$port/accounts/${user.id}",
                HttpMethod.GET,
                request,
                SellUserDTO::class.java
            )

        if (response.body == null) {
            Assert.fail()
        } else {
            Assert.assertEquals(response.body, user.getDTO())
        }
    }

    @Test
    fun `show user by username`() {
        //create new user
        val user = userRepo.save(SellUser("testUsername", "pName", "pass", "test@mail.com"))

        val request = HttpEntity(user.username)

        val response = restTemplate.exchange(
            "http://localhost:$port/accounts/username/${user.username}",
            HttpMethod.GET,
            request,
            SellUserDTO::class.java
        )

        Assert.assertEquals(response.body, user.getDTO())
    }

    @Test
    fun `show user by email`() {
        val user = userRepo.save(SellUser("testUsername", "pName", "pass", "test@mail.com"))

        val request = HttpEntity(user.email)

        val response = restTemplate.exchange(
            "http://localhost:$port/accounts/email/${user.email}",
            HttpMethod.GET,
            request,
            SellUserDTO::class.java
        )

        Assert.assertEquals(response.body, user.getDTO())
    }
}