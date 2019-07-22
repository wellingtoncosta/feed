package io.github.wellingtoncosta.feed

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import io.github.wellingtoncosta.feed.WebServer.server
import io.github.wellingtoncosta.feed.app.ui.listposts.ListPostsActivity
import io.github.wellingtoncosta.feed.extension.asJson
import io.github.wellingtoncosta.feed.extensions.dispatches
import io.github.wellingtoncosta.feed.extensions.responses
import org.hamcrest.CoreMatchers.not
import org.junit.*
import org.junit.runner.RunWith
import android.content.Intent

@LargeTest
@RunWith(AndroidJUnit4::class)
class ListPostsActivityTest {

    @Rule @JvmField val activityRule = ActivityTestRule(ListPostsActivity::class.java, true, true)

    @Test fun shouldListWithEmptyResponse() {
        server.dispatcher = dispatches { path ->
            when (path) {
                "/posts" -> 200 responses "payloads/empty-list-response.json".asJson()
                "/users/1" -> 200 responses "payloads/user-response.json".asJson()
                else -> 404 responses null
            }
        }

        activityRule.launchActivity(Intent())

        onView(withId(R.id.list_posts_root))
            .check(matches(isDisplayed()))

        onView(withId(R.id.no_posts_text))
            .check(matches(isDisplayed()))

        onView(withId(R.id.recycler_view))
            .check(matches(not(isDisplayed())))
    }

    @Test fun shouldListWithNonEmptyResponse() {
        server.dispatcher = dispatches { path ->
            when (path) {
                "/posts" -> 200 responses "payloads/five-posts-response.json".asJson()
                "/users/1" -> 200 responses "payloads/user-response.json".asJson()
                else -> 404 responses null
            }
        }

        activityRule.launchActivity(Intent())

        onView(withId(R.id.list_posts_root))
            .check(matches(isDisplayed()))

        onView(withId(R.id.recycler_view))
            .check(matches(isDisplayed()))

        onView(withId(R.id.no_posts_text))
            .check(matches(not(isDisplayed())))
    }

    companion object {

        @BeforeClass @JvmStatic fun startServer() = WebServer.startHttps()

        @AfterClass @JvmStatic fun shutdownServer() = WebServer.shutdown()

    }

}
