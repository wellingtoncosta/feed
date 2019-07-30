package io.github.wellingtoncosta.feed

import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.idling.CountingIdlingResource
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import io.github.wellingtoncosta.feed.app.IDLING_POST_DETAILS
import io.github.wellingtoncosta.feed.app.startHttpsServer
import io.github.wellingtoncosta.feed.app.ui.postdetails.PostDetailsActivity
import io.github.wellingtoncosta.feed.extension.asJson
import io.github.wellingtoncosta.feed.extensions.dispatches
import io.github.wellingtoncosta.feed.extensions.responses
import io.github.wellingtoncosta.feed.robot.postDetails
import okhttp3.mockwebserver.MockWebServer
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.qualifier.named
import org.koin.test.KoinTest
import org.koin.test.inject

@LargeTest
@RunWith(AndroidJUnit4::class)
class PostDetailsActivityTest {

    private val idlingResource by inject<CountingIdlingResource>(named(IDLING_POST_DETAILS))

    @Rule @JvmField val activityRule = ActivityTestRule(PostDetailsActivity::class.java)

    @Test fun shouldShowPostDetailsWithComments() {

        postDetails(activityRule) {

            dispatchPostWithCommentsResponse()

            launch()

            assertPostWithComments()
        }
    }

    @Test fun shouldShowPostDetailsWithNoComments() {
        postDetails(activityRule) {

            dispatchPostWithNoCommentsResponse()

            launch()

            assertPostWithNoComments()

        }
    }

    private fun dispatchPostWithCommentsResponse() {
        server.dispatcher = dispatches { path ->
            when (path) {
                "/posts/1" -> 200 responses "payloads/post-response.json".asJson()
                "/users/1" -> 200 responses "payloads/user-response.json".asJson()
                "/comments?postId=1" -> 200 responses "payloads/comments-by-post-response.json".asJson()
                else -> 404 responses null
            }
        }
    }

    private fun dispatchPostWithNoCommentsResponse() {
        server.dispatcher = dispatches { path ->
            when (path) {
                "/posts/1" -> 200 responses "payloads/post-response.json".asJson()
                "/users/1" -> 200 responses "payloads/user-response.json".asJson()
                "/comments?postId=1" -> 200 responses "payloads/empty-list-response.json".asJson()
                else -> 404 responses null
            }
        }
    }

    companion object : KoinTest {

        private val idlingResource by inject<CountingIdlingResource>(named(IDLING_POST_DETAILS))

        private lateinit var server: MockWebServer

        @BeforeClass @JvmStatic fun beforeAll() {
            server = startHttpsServer()

            IdlingRegistry.getInstance().register(idlingResource)
        }

        @AfterClass @JvmStatic fun afterAll() {
            server.shutdown()

            IdlingRegistry.getInstance().unregister(idlingResource)
        }

    }

}
