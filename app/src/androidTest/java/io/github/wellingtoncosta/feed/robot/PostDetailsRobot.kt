package io.github.wellingtoncosta.feed.robot

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import io.github.wellingtoncosta.feed.R
import io.github.wellingtoncosta.feed.app.ui.listposts.ListPostsActivity.Companion.POST_ID
import io.github.wellingtoncosta.feed.app.ui.postdetails.PostDetailsActivity
import org.hamcrest.CoreMatchers.not

class PostDetailsRobot(
    private val activityRule: ActivityTestRule<PostDetailsActivity>
) {

    fun assertPostWithComments() {
        onView(withId(R.id.post_details_root))
            .check(matches(isDisplayed()))

        onView(withId(R.id.load_post_details_error_text))
            .check(matches(not(isDisplayed())))

        onView(withId(R.id.comments_recycler_view))
            .check(matches(isDisplayed()))
    }

    fun assertPostWithNoComments() {
        onView(withId(R.id.post_details_root))
            .check(matches(isDisplayed()))

        onView(withId(R.id.load_post_details_error_text))
            .check(matches(not(isDisplayed())))

        onView(withId(R.id.comments_recycler_view))
            .check(matches(not(isDisplayed())))

        onView(withId(R.id.no_comments_text))
            .check(matches(isDisplayed()))
    }

    fun launch(): Unit = activityRule.launchActivity(Intent().apply {
        putExtra(POST_ID, 1L)
    }).let { Unit }

}

fun postDetails(
    activityRule: ActivityTestRule<PostDetailsActivity>,
    body: PostDetailsRobot.() -> Unit
) = PostDetailsRobot(activityRule).apply(body)
