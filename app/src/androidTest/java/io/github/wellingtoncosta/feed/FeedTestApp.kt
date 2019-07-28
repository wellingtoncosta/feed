package io.github.wellingtoncosta.feed

import androidx.test.espresso.idling.CountingIdlingResource
import io.github.wellingtoncosta.feed.network.CommentFuelApi
import io.github.wellingtoncosta.feed.network.PostFuelApi
import io.github.wellingtoncosta.feed.network.UserFuelApi
import io.github.wellingtoncosta.feed.app.FeedApp
import io.github.wellingtoncosta.feed.app.config.cacheModule
import io.github.wellingtoncosta.feed.app.config.interactorModule
import io.github.wellingtoncosta.feed.app.config.repositoryModule
import io.github.wellingtoncosta.feed.app.config.viewModelModule
import io.github.wellingtoncosta.feed.infrastructure.network.api.CommentApi
import io.github.wellingtoncosta.feed.infrastructure.network.api.PostApi
import io.github.wellingtoncosta.feed.infrastructure.network.api.UserApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
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

private val networkModule = module {
    single { Json(JsonConfiguration.Stable.copy(strictMode = false)) }
    single<PostApi> { PostFuelApi(get(), get()) }
    single<CommentApi> { CommentFuelApi(get(), get()) }
    single<UserApi> { UserFuelApi(get(), get()) }
}

const val IDLING_TEST = "IDLING_TEST"

private val testModule = module {
    single { CountingIdlingResource(IDLING_TEST) }
}
