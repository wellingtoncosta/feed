package io.github.wellingtoncosta.feed.infrastructure.network.api

import io.github.wellingtoncosta.feed.infrastructure.network.entity.PostResponse

interface PostApi {

    suspend fun getAllPosts(): List<PostResponse>

    suspend fun getPostById(postId: Long): PostResponse?

}
