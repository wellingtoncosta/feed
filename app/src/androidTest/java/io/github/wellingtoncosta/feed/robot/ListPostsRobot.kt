package io.github.wellingtoncosta.feed.robot

import android.content.Intent
import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import io.github.wellingtoncosta.feed.R
import io.github.wellingtoncosta.feed.app.ui.listposts.ListPostsActivity
import io.github.wellingtoncosta.feed.assertion.RecyclerViewItemCountAssertion.Companion.hasItemCount
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.not
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed

class ListPostsRobot(
    private val activityRule: ActivityTestRule<ListPostsActivity>
) {

    fun assertNoPosts() {
        onView(withId(R.id.list_posts_root))
            .check(matches(isDisplayed()))

        onView(withId(R.id.no_posts_text))
            .check(matches(isDisplayed()))

        onView(withId(R.id.recycler_view))
            .check(matches(not(isDisplayed())))
    }

    fun assertFivePosts() {
        onView(withId(R.id.list_posts_root))
            .check(matches(isDisplayed()))

        onView(withId(R.id.recycler_view))
            .check(matches(isDisplayed()))

        onView(withId(R.id.no_posts_text))
            .check(matches(not(isDisplayed())))

        onView(
            allOf<View>(
                withId(R.id.recycler_view),
                isDescendantOfA(withId(R.id.list_posts_root))
            )
        ).check(hasItemCount(5))
    }

    fun assertDisplayErrorMessage() {
        onView(withId(R.id.list_posts_root))
            .check(matches(isDisplayed()))

        onView(withId(R.id.recycler_view))
            .check(matches(not(isDisplayed())))

        onView(withId(R.id.no_posts_text))
            .check(matches(isDisplayed()))

        onView(withText(R.string.load_posts_error))
            .inRoot(withDecorView(not(activityRule.activity.window.decorView)))
            .check(matches(isDisplayed()))

    }

    fun launch(): Unit = activityRule.launchActivity(Intent()).let { Unit }

}

fun listPosts(
    activityRule: ActivityTestRule<ListPostsActivity>,
    body: ListPostsRobot.() -> Unit
) = ListPostsRobot(activityRule).apply(body)
