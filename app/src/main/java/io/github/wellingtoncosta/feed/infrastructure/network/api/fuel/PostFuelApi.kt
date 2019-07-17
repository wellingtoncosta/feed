package io.github.wellingtoncosta.feed.infrastructure.network.api.fuel

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.coroutines.awaitString
import com.github.kittinunf.fuel.coroutines.awaitStringResponse
import io.github.wellingtoncosta.feed.BuildConfig.API_URL
import io.github.wellingtoncosta.feed.infrastructure.network.api.PostApi
import io.github.wellingtoncosta.feed.infrastructure.network.entity.PostResponse
import kotlinx.serialization.json.Json
import kotlinx.serialization.list

class PostFuelApi(
    private val apiUrl: String = API_URL,
    private val json: Json
) : PostApi {

    override suspend fun getAllPosts() = Fuel.get("$apiUrl/posts").awaitString().let {
        json.parse(PostResponse.serializer().list, it)
    }

    override suspend fun getPostById(postId: Long) =
        Fuel.get("$apiUrl/posts/$postId").awaitStringResponse().run {
            if(second.statusCode != HTTP_200) null
            else json.parse(PostResponse.serializer(), third)
        }

}
