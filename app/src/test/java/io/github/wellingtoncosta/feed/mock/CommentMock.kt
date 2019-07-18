package io.github.wellingtoncosta.feed.mock

import io.github.wellingtoncosta.feed.domain.entity.Comment

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

}