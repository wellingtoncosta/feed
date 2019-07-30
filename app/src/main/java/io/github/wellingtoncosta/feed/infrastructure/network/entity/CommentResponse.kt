package io.github.wellingtoncosta.feed.infrastructure.network.entity

import io.github.wellingtoncosta.feed.domain.entity.Comment
import kotlinx.serialization.Serializable

@Serializable data class CommentResponse(
    val id: Long,
    val postId: Long,
    val name: String,
    val email: String,
    val body: String
)

fun CommentResponse.toDomain() = Comment(
    id = id,
    name = name,
    email = email,
    body = body
)

fun List<CommentResponse>.toDomai() = map { it.toDomain() }
