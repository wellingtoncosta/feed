package io.github.wellingtoncosta.feed.infrastructure.network.api.fuel

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.coroutines.awaitString
import io.github.wellingtoncosta.feed.infrastructure.network.api.CommentApi
import io.github.wellingtoncosta.feed.infrastructure.network.entity.CommentResponse
import kotlinx.serialization.json.Json
import kotlinx.serialization.list

class CommentFuelApi(
    private val json: Json
) : CommentApi {

    override suspend fun getCommentsByPostId(postId: Long) = Fuel.get("/comments?postId=$postId")
        .awaitString().let {
            json.parse(CommentResponse.serializer().list, it)
        }

}
