package eu.sell.accountservice

import eu.sell.accountservice.persistence.dto.ChangePasswordForm
import eu.sell.accountservice.persistence.dto.NewUserDTO
import eu.sell.accountservice.persistence.dto.SellUserDTO
import eu.sell.accountservice.repositories.IUserRepo
import eu.sell.accountservice.services.AccountService
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
class MainControllerTests {

    @Autowired
    lateinit var userRepo: IUserRepo

    @Autowired
    lateinit var accountService: AccountService

    @LocalServerPort
    private var port: Int = 0

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Before
    fun prepareTests() {
        //clear user database
        userRepo.deleteAll()
    }

    @Test
    fun contextLoads() {
    }

    @Test
    fun `user created successfully`() {
        val newUserDTO = NewUserDTO(
            "testUsername", "testPublicName",
            "1111", "test@mail.com"
        )
        val request = HttpEntity(newUserDTO)
        val response = restTemplate.exchange(
            "http://localhost:$port/accounts/create",
            HttpMethod.POST,
            request,
            SellUserDTO::class.java
        )

        val user = userRepo.findByEmail(newUserDTO.email).get()

        Assert.assertEquals(user.getDTO(), response.body)
    }

    @Test
    fun `user created successfully with JSON`() {
        val json = "{" +
                "\"username\":\"testUsername1\"," +
                " \"public_name\" : \"testPublicName\"," +
                "\"password\" : \"testPass\"," +
                "\"email\" : \"test@mail.com\"" +
                "}"
    }

    @Test
    fun `change password successful`() {
        val user = accountService.registerUser(NewUserDTO("test", "testPublicName", "1234", "email.test@mail.com"))
        val changeForm = ChangePasswordForm("1234", "12345")
        val request = HttpEntity(changeForm)
        val response = restTemplate.exchange(
            "http://localhost:$port/accounts/update/password/${user.id}",
            HttpMethod.PUT,
            request,
            SellUserDTO::class.java
        )
        Assert.assertEquals(response.body!!.username, user.username)
    }
}