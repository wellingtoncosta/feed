package io.github.wellingtoncosta.feed.mock

import io.github.wellingtoncosta.feed.domain.entity.Comment
import io.github.wellingtoncosta.feed.infrastructure.network.entity.CommentResponse

object CommentMock {

    val threeComments: List<Comment> get() = listOf(
        Comment(
            id = 1L,
            name = "String",
            email = "String",
            body = "String"
        ),
        Comment(
            id = 2L,
            name = "String",
            email = "String",
            body = "String"
        ),
        Comment(
            id = 3L,
            name = "String",
            email = "String",
            body = "String"
        )
    )

    val threeCommentsResponse: List<CommentResponse> get() = threeComments.map { it.toResponse() }

    private fun Comment.toResponse() = CommentResponse(
        id = this.id,
        name = this.name,
        email = this.email,
        body = this.body,
        postId = 1L
    )

}