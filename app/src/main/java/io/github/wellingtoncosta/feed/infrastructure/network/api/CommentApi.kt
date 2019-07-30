package io.github.wellingtoncosta.feed.infrastructure.network.api

import io.github.wellingtoncosta.feed.infrastructure.network.entity.CommentResponse

interface CommentApi {

    suspend fun getCommentsByPostId(postId: Long): List<CommentResponse>

}
