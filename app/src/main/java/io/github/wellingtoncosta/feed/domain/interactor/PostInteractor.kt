package io.github.wellingtoncosta.feed.domain.interactor

import io.github.wellingtoncosta.feed.domain.entity.Post

interface PostInteractor {

    suspend fun getPosts(): List<Post>

    suspend fun getPostById(postId: Long): Post?

}
