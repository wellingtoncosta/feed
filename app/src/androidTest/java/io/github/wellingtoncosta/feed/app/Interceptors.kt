package io.github.wellingtoncosta.feed.app

import androidx.test.espresso.idling.CountingIdlingResource
import com.github.kittinunf.fuel.core.FoldableRequestInterceptor
import com.github.kittinunf.fuel.core.FoldableResponseInterceptor
import com.github.kittinunf.fuel.core.RequestTransformer
import com.github.kittinunf.fuel.core.ResponseTransformer
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.qualifier.named
import timber.log.Timber

object IdlingRequestInterceptor : FoldableRequestInterceptor, KoinComponent {

    private val idlingResource by inject<CountingIdlingResource>(named(IDLING_LIST_POSTS))

    override fun invoke(next: RequestTransformer): RequestTransformer {
        return { request ->
            idlingResource.increment()
            Timber.d("running request to ${request.url}")
            next(request)
        }
    }

}

object IdlingResponseInterceptor : FoldableResponseInterceptor, KoinComponent {

    private val idlingResource by inject<CountingIdlingResource>(named(IDLING_POST_DETAILS))

    override fun invoke(next: ResponseTransformer): ResponseTransformer {
        return { request, response ->
            idlingResource.decrement()
            next(request, response)
        }
    }

}
