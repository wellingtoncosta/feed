package io.github.wellingtoncosta.feed.infrastructure.network.entity

import io.github.wellingtoncosta.feed.domain.entity.Comment
import io.github.wellingtoncosta.feed.domain.entity.Post
import io.github.wellingtoncosta.feed.domain.entity.User
import kotlinx.serialization.Serializable

@Serializable data class PostResponse(
    val id: Long,
    val userId: Long,
    val title: String,
    val body: String
)

fun PostResponse.toDomain(user: User) = Post(
    id = id,
    title = title,
    user = user,
    body = body,
    comments = mutableListOf()
)

fun PostResponse.toDomain(user: User, comments: List<Comment>) = Post(
    id = id,
    title = title,
    user = user,
    body = body,
    comments = comments
)
