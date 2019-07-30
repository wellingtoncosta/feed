package io.github.wellingtoncosta.feed.assertion

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import org.hamcrest.CoreMatchers.`is`

class RecyclerViewItemCountAssertion
private constructor(private val expectedCount: Int) : ViewAssertion {

    override fun check(view: View, noViewFoundException: NoMatchingViewException?) {
        if (noViewFoundException != null) {
            throw noViewFoundException
        }

        val recyclerView = view as RecyclerView
        val actualItemCount = recyclerView.adapter?.itemCount
        assertThat<Int>(actualItemCount, `is`<Int>(expectedCount))
    }

    companion object {
        fun hasItemCount(expectedCount: Int): RecyclerViewItemCountAssertion {
            return RecyclerViewItemCountAssertion(expectedCount)
        }
    }

}