package io.github.wellingtoncosta.feed.app.config

import io.github.wellingtoncosta.feed.app.ui.listposts.ListPostsViewModel
import io.github.wellingtoncosta.feed.app.ui.postdetails.PostDetailsViewModel
import io.github.wellingtoncosta.feed.domain.interactor.PostInteractor
import io.github.wellingtoncosta.feed.domain.interactor.implementation.PostInteractorImpl
import io.github.wellingtoncosta.feed.domain.repository.PostRepository
import io.github.wellingtoncosta.feed.domain.repository.UserRepository
import io.github.wellingtoncosta.feed.infrastructure.cache.UserCache
import io.github.wellingtoncosta.feed.infrastructure.cache.memory.UserMemoryCache
import io.github.wellingtoncosta.feed.infrastructure.network.api.CommentApi
import io.github.wellingtoncosta.feed.infrastructure.network.api.PostApi
import io.github.wellingtoncosta.feed.infrastructure.network.api.UserApi
import io.github.wellingtoncosta.feed.infrastructure.network.api.fuel.CommentFuelApi
import io.github.wellingtoncosta.feed.infrastructure.network.api.fuel.PostFuelApi
import io.github.wellingtoncosta.feed.infrastructure.network.api.fuel.UserFuelApi
import io.github.wellingtoncosta.feed.infrastructure.repository.PostRepositoryImpl
import io.github.wellingtoncosta.feed.infrastructure.repository.UserRepositoryImp
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val networkModule = module {
    single { Json(JsonConfiguration.Stable.copy(strictMode = false)) }

    single<PostApi> { PostFuelApi(json = get()) }
    single<CommentApi> { CommentFuelApi(json = get()) }
    single<UserApi> { UserFuelApi(json = get()) }
}

val cacheModule = module {
    single<UserCache> { UserMemoryCache() }
}

val repositoryModule = module {
    single<UserRepository> { UserRepositoryImp(get(), get()) }
    single<PostRepository> { PostRepositoryImpl(get(), get(), get()) }
}

val interactorModule = module {
    single<PostInteractor> { PostInteractorImpl(get()) }
}

val viewModelModule = module {
    viewModel { ListPostsViewModel(get()) }
    viewModel { PostDetailsViewModel(get()) }
}
