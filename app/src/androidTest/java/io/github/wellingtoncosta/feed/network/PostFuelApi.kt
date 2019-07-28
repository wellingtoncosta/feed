package io.github.wellingtoncosta.feed.network

import androidx.test.espresso.idling.CountingIdlingResource
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.coroutines.awaitString
import com.github.kittinunf.fuel.coroutines.awaitStringResponse
import io.github.wellingtoncosta.feed.infrastructure.network.api.PostApi
import io.github.wellingtoncosta.feed.infrastructure.network.api.fuel.HTTP_200
import io.github.wellingtoncosta.feed.infrastructure.network.entity.PostResponse
import kotlinx.serialization.json.Json
import kotlinx.serialization.list

class PostFuelApi(
    private val json: Json,
    private val idlingResource: CountingIdlingResource
) : PostApi {

    override suspend fun getAllPosts() = Fuel.get("/posts")
        .also { idlingResource.increment() }
        .awaitString()
        .let { json.parse(PostResponse.serializer().list, it) }
        .also { idlingResource.decrement() }

    override suspend fun getPostById(postId: Long) =
        Fuel.get("/posts/$postId")
            .also { idlingResource.increment() }
            .awaitStringResponse()
            .run {
                if(second.statusCode != HTTP_200) null
                else json.parse(PostResponse.serializer(), third)
            }.also { idlingResource.decrement() }

}