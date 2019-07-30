package io.github.wellingtoncosta.feed

import androidx.test.espresso.IdlingRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import io.github.wellingtoncosta.feed.app.startHttpsServer
import io.github.wellingtoncosta.feed.app.ui.CoroutinesIdlingResource
import io.github.wellingtoncosta.feed.app.ui.listposts.ListPostsActivity
import io.github.wellingtoncosta.feed.extension.asJson
import io.github.wellingtoncosta.feed.extensions.dispatches
import io.github.wellingtoncosta.feed.extensions.responses
import io.github.wellingtoncosta.feed.robot.listPosts
import okhttp3.mockwebserver.MockWebServer
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class ListPostsActivityTest {

    @Rule @JvmField val activityRule = ActivityTestRule(ListPostsActivity::class.java, true, false)

    @Test fun shouldListWithEmptyResponse() {

        listPosts(activityRule) {

            dispatchEmptyResponse()

            launch()

            assertNoPosts()

        }

    }

    @Test fun shouldListWithNonEmptyResponse() {

        listPosts(activityRule) {

            dispatchFivePostsResponse()

            launch()

            assertFivePosts()

        }

    }

    @Test fun shouldDisplayErrorMessage() {

        listPosts(activityRule) {

            dispatchInternalServerError()

            launch()

            assertDisplayErrorMessage()

        }

    }

    private fun dispatchEmptyResponse() {
        server.dispatcher = dispatches { path ->
            when (path) {
                "/posts" -> 200 responses "payloads/empty-list-response.json".asJson()
                else -> 404 responses null
            }
        }
    }

    private fun dispatchFivePostsResponse() {
        server.dispatcher = dispatches { path ->
            when (path) {
                "/posts" -> 200 responses "payloads/five-posts-response.json".asJson()
                "/users/1" -> 200 responses "payloads/user-response.json".asJson()
                else -> 404 responses null
            }
        }
    }

    private fun dispatchInternalServerError() {
        server.dispatcher = dispatches { path ->
            when (path) {
                "/posts" -> 500 responses "payloads/empty-list-response.json".asJson()
                else -> 404 responses null
            }
        }
    }

    companion object {

        private lateinit var server: MockWebServer

        @BeforeClass @JvmStatic fun beforeAll() {
            server = startHttpsServer()

            IdlingRegistry.getInstance().register(CoroutinesIdlingResource.idlingResource)
        }

        @AfterClass @JvmStatic fun afterAll() {
            server.shutdown()

            IdlingRegistry.getInstance().unregister(CoroutinesIdlingResource.idlingResource)
        }

    }

}
