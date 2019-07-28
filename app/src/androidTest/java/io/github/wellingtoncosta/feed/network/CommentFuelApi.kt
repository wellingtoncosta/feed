package io.github.wellingtoncosta.feed.network

import androidx.test.espresso.idling.CountingIdlingResource
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.coroutines.awaitString
import io.github.wellingtoncosta.feed.infrastructure.network.api.CommentApi
import io.github.wellingtoncosta.feed.infrastructure.network.entity.CommentResponse
import kotlinx.serialization.json.Json
import kotlinx.serialization.list

class CommentFuelApi(
    private val json: Json,
    private val idlingResource: CountingIdlingResource
) : CommentApi {

    override suspend fun getCommentsByPostId(postId: Long) = Fuel.get("/comments?postId=$postId")
        .also { idlingResource.increment() }
        .awaitString()
        .let { json.parse(CommentResponse.serializer().list, it) }
        .also { idlingResource.decrement() }

}
