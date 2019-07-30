package io.github.wellingtoncosta.feed.infrastructure.api

import io.github.wellingtoncosta.feed.asJson
import io.github.wellingtoncosta.feed.extensions.dispatches
import io.github.wellingtoncosta.feed.extensions.responses
import io.github.wellingtoncosta.feed.infrastructure.network.api.fuel.CommentFuelApi
import io.github.wellingtoncosta.feed.infrastructure.network.entity.CommentResponse
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

class CommentApiTest {

    private val json = Json(JsonConfiguration.Stable.copy(strictMode = false))

    private val api = CommentFuelApi(json)

    @Test fun `should fetch comments by post with an empty result`() = runBlocking {
        val response = "/payloads/empty-list-response.json".asJson()

        server.dispatcher = dispatches { path ->
            when (path) {
                "/comments?postId=1" -> 200 responses response
                else -> 404 responses null
            }
        }

        val result = api.getCommentsByPostId(1)

        val expected = json.parse(CommentResponse.serializer().list, response)

        assertEquals(expected, result)
    }

    @Test fun `should fetch comments by post`() = runBlocking {
        val response = "/payloads/comments-by-post-response.json".asJson()

        server.dispatcher = dispatches { path ->
            when (path) {
                "/comments?postId=1" -> 200 responses response
                else -> 404 responses null
            }
        }

        val result = api.getCommentsByPostId(1)

        val expected = json.parse(CommentResponse.serializer().list, response)

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
