package io.github.wellingtoncosta.feed.app.ui

import androidx.test.espresso.idling.CountingIdlingResource

object CoroutinesIdlingResource {

    private val idlingResourceName = "COROUTINES_IDLING_RESOURCE"

    val idlingResource = CountingIdlingResource(idlingResourceName)

}