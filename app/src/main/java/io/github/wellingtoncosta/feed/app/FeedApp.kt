package io.github.wellingtoncosta.feed.app

import android.app.Application
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelManager
import io.github.wellingtoncosta.feed.BuildConfig.API_URL
import io.github.wellingtoncosta.feed.BuildConfig.DEBUG
import io.github.wellingtoncosta.feed.app.config.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class FeedApp : Application() {

    override fun onCreate() {
        super.onCreate()

        if(DEBUG)
            Timber.plant(Timber.DebugTree())

        FuelManager.instance.basePath = API_URL

        startKoin {
            androidLogger()

            androidContext(this@FeedApp)

            modules(listOf(
                networkModule,
                cacheModule,
                repositoryModule,
                interactorModule,
                viewModelModule
            ))
        }
    }

}