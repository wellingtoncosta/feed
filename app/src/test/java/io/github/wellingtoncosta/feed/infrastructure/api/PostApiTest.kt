package io.github.wellingtoncosta.feed.infrastructure.api

import com.github.kittinunf.fuel.core.FuelError
import io.github.wellingtoncosta.feed.extensions.asJson
import io.github.wellingtoncosta.feed.extensions.dispatches
import io.github.wellingtoncosta.feed.extensions.responses
import io.github.wellingtoncosta.feed.infrastructure.network.api.fuel.PostFuelApi
import io.github.wellingtoncosta.feed.infrastructure.network.entity.PostResponse
import io.github.wellingtoncosta.feed.startHttpServer
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import kotlinx.serialization.list
import okhttp3.mockwebserver.MockWebServer
import org.junit.AfterClass
import org.junit.Assert.assertEquals
import org.junit.BeforeClass
import org.junit.Test

class PostApiTest {

    private val json = Json(JsonConfiguration.Stable.copy(strictMode = false))

    private val api = PostFuelApi(json)

    @Test fun `should fetch posts with an empty result`() = runBlocking {
        val payload = "/payloads/empty-list-response.json".asJson()

        server.dispatcher = dispatches { path ->
            when (path) {
                "/posts" -> 200 responses payload
                else -> 404 responses null
            }
        }

        val result = api.getAllPosts()

        val expected = json.parse(PostResponse.serializer().list, payload)

        assertEquals(expected, result)
    }

    @Test fun `should fetch posts with five results`() = runBlocking {
        val payload = "/payloads/five-posts-response.json".asJson()

        server.dispatcher = dispatches { path ->
            when (path) {
                "/posts" -> 200 responses payload
                else -> 404 responses null
            }
        }

        val result = api.getAllPosts()

        val expected = json.parse(PostResponse.serializer().list, payload)

        assertEquals(expected, result)
    }

    @Test(expected = FuelError::class)
    fun `should return status 500 when fetch posts`() = runBlocking {
        server.dispatcher = dispatches { path ->
            when (path) {
                "/posts" -> 500 responses null
                else -> 404 responses null
            }
        }

        api.getAllPosts()
    }.let { Unit }

    @Test fun `should fetch a  post by id`() = runBlocking {
        val payload = "/payloads/post-response.json".asJson()

        server.dispatcher = dispatches { path ->
            when (path) {
                "/posts/1" -> 200 responses payload
                else -> 404 responses null
            }
        }

        val result = api.getPostById(1)

        val expected = json.parse(PostResponse.serializer(), payload)

        assertEquals(expected, result)
    }

    @Test(expected = FuelError::class)
    fun `should not fetch a  post by id`() = runBlocking {
        val payload = "/payloads/post-response.json".asJson()

        server.dispatcher = dispatches { path ->
            when (path) {
                "/posts/2" -> 200 responses payload
                else -> 404 responses null
            }
        }

        val result = api.getPostById(1)

        val expected = json.parse(PostResponse.serializer(), payload)

        assertEquals(expected, result)
    }

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
