package io.github.wellingtoncosta.feed

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import io.github.wellingtoncosta.feed.WebServer.server
import io.github.wellingtoncosta.feed.app.ui.listposts.ListPostsActivity
import io.github.wellingtoncosta.feed.extension.asJson
import io.github.wellingtoncosta.feed.extensions.dispatches
import io.github.wellingtoncosta.feed.extensions.responses
import io.github.wellingtoncosta.feed.robot.listPosts
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

    companion object {

        @BeforeClass @JvmStatic fun startServer() = WebServer.startHttps()

        @AfterClass @JvmStatic fun shutdownServer() = WebServer.shutdown()

    }

}
