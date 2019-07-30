package io.github.wellingtoncosta.feed.domain.repository

import io.github.wellingtoncosta.feed.domain.entity.Post

interface PostRepository {

    suspend fun getAllPosts(): List<Post>

    suspend fun getPostById(postId: Long): Post

}
