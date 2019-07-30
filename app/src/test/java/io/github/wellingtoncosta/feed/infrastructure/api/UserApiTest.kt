package io.github.wellingtoncosta.feed.infrastructure.api

import com.github.kittinunf.fuel.core.FuelError
import io.github.wellingtoncosta.feed.extensions.asJson
import io.github.wellingtoncosta.feed.extensions.dispatches
import io.github.wellingtoncosta.feed.extensions.responses
import io.github.wellingtoncosta.feed.infrastructure.network.api.fuel.UserFuelApi
import io.github.wellingtoncosta.feed.infrastructure.network.entity.UserResponse
import io.github.wellingtoncosta.feed.startHttpServer
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import okhttp3.mockwebserver.MockWebServer
import org.junit.AfterClass
import org.junit.Assert.assertEquals
import org.junit.BeforeClass
import org.junit.Test

class UserApiTest {

    private val json = Json(JsonConfiguration.Stable.copy(strictMode = false))

    private val api = UserFuelApi(json)

    @Test fun `should fetch a user by id`() = runBlocking {
        val payload = "/payloads/user-response.json".asJson()

        server.dispatcher = dispatches { path ->
            when (path) {
                "/users/1" -> 200 responses payload
                else -> 404 responses null
            }
        }

        val result = api.getUserById(1)

        val expected = json.parse(UserResponse.serializer(), payload)

        assertEquals(expected, result)
    }

    @Test(expected = FuelError::class)
    fun `should not fetch a user by id`() = runBlocking {
        server.dispatcher = dispatches { path ->
            when (path) {
                "/users/1" -> 404 responses null
                else -> 404 responses null
            }
        }

        api.getUserById(1)
    }.let { Unit }

    companion object {

        private lateinit var server: MockWebServer

        @BeforeClass @JvmStatic fun beforeAll() {
            server = startHttpServer()
        }

        @AfterClass @JvmStatic fun afterAll() {
            server.shutdown()
        }

    }

}
