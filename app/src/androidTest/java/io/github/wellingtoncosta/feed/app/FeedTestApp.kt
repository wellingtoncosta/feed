package io.github.wellingtoncosta.feed.app

import androidx.test.espresso.idling.CountingIdlingResource
import io.github.wellingtoncosta.feed.app.config.koin.*
import org.koin.core.qualifier.named
import org.koin.dsl.module

class FeedTestApp : FeedApp() {

    override fun koinModules() = listOf(
        networkModule,
        cacheModule,
        repositoryModule,
        interactorModule,
        viewModelModule,
        testModule
    )

}

const val IDLING_LIST_POSTS = "IDLING_LIST_POSTS"
const val IDLING_POST_DETAILS = "IDLING_POST_DETAILS"

private val testModule = module {
    single(named(IDLING_LIST_POSTS)) {
        CountingIdlingResource(IDLING_LIST_POSTS)
    }

    single(named(IDLING_POST_DETAILS)) {
        CountingIdlingResource(IDLING_POST_DETAILS)
    }
}
