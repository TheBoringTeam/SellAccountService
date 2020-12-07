package eu.sell.accountservice

import eu.sell.accountservice.persistence.dto.ChangePasswordReqModel
import eu.sell.accountservice.persistence.dto.RegisterModel
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
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
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
    fun `user created successfully`() {
        val newUserDTO = RegisterModel(
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
        val json = "{\n" +
                "    \"username\": \"testusername\",\n" +
                "    \"public_name\": \"testPublicName\",\n" +
                "    \"password\": \"password1\",\n" +
                "    \"email\": \"email@mail.com\"\n" +
                "}"
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val request = HttpEntity(json, headers)
        val response = restTemplate.exchange(
            "http://localhost:$port/accounts/create",
            HttpMethod.POST,
            request,
            SellUserDTO::class.java
        )

        val user = userRepo.findByUsername("testusername").get()
        Assert.assertEquals(user.getDTO(), response.body)
    }

    @Test
    fun `change password successful`() {
        var user = accountService.registerUser(RegisterModel("test", "testPublicName", "1234", "email.test@mail.com"))
        val changeForm = ChangePasswordReqModel("1234", "12345")
        val request = HttpEntity(changeForm)
        val response = restTemplate.exchange(
            "http://localhost:$port/accounts/update/password/${user.id}",
            HttpMethod.PUT,
            request,
            SellUserDTO::class.java
        )

        user = accountService.findById(user.id)
        Assert.assertTrue(accountService.isPasswordEquals(user, "12345"))
        Assert.assertEquals(response.body!!.username, user.username)
    }

    @Test
    fun `change password successful with JSON`() {
        var user = accountService.registerUser(RegisterModel("test", "testPublicName", "1234", "email.test@mail.com"))
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val json = "{\n" +
                "    \"old_password\": \"1234\",\n" +
                "    \"new_password\": \"12345\"\n" +
                "}"
        val request = HttpEntity(json, headers)
        val response = restTemplate.exchange(
            "http://localhost:$port/accounts/update/password/${user.id}",
            HttpMethod.PUT,
            request,
            SellUserDTO::class.java
        )
        user = accountService.findById(user.id)
        Assert.assertTrue(accountService.isPasswordEquals(user, "12345"))
        Assert.assertEquals(response.body!!.username, user.username)
    }
}