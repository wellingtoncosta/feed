package io.github.wellingtoncosta.feed.app

import android.app.Application
import com.github.kittinunf.fuel.core.FuelManager
import io.github.wellingtoncosta.feed.BuildConfig.API_URL
import io.github.wellingtoncosta.feed.BuildConfig.DEBUG
import io.github.wellingtoncosta.feed.app.config.cacheModule
import io.github.wellingtoncosta.feed.app.config.interactorModule
import io.github.wellingtoncosta.feed.app.config.networkModule
import io.github.wellingtoncosta.feed.app.config.repositoryModule
import io.github.wellingtoncosta.feed.app.config.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

open class FeedApp : Application() {

    override fun onCreate() {

        super.onCreate()

        if(DEBUG) Timber.plant(Timber.DebugTree())

        FuelManager.instance.basePath = API_URL

        startKoin()

    }

    private fun startKoin() {
        startKoin {
            androidLogger()

            androidContext(this@FeedApp)

            modules(koinModules())
        }
    }

    open fun koinModules() = listOf(
        networkModule,
        cacheModule,
        repositoryModule,
        interactorModule,
        viewModelModule
    )

}
