package io.github.wellingtoncosta.feed.mock

import io.github.wellingtoncosta.feed.domain.entity.Post
import io.github.wellingtoncosta.feed.infrastructure.network.entity.PostResponse
import io.github.wellingtoncosta.feed.mock.UserMock.oneUser

object PostMock {

    val onePost: Post get() = Post(
        id = 1,
        user = oneUser,
        title = "String",
        body = "String",
        comments = emptyList()
    )

    val onePostResponse: PostResponse get() = onePost.toResponse()

    val fivePosts: List<Post> get() = listOf(
        Post(
            id = 1,
            user = oneUser,
            title = "String",
            body = "String",
            comments = emptyList()
        ),
        Post(
            id = 2,
            user = oneUser,
            title = "String",
            body = "String",
            comments = emptyList()
        ),
        Post(
            id = 3,
            user = oneUser,
            title = "String",
            body = "String",
            comments = emptyList()
        ),
        Post(
            id = 4,
            user = oneUser,
            title = "String",
            body = "String",
            comments = emptyList()
        ),
        Post(
            id = 5,
            user = oneUser,
            title = "String",
            body = "String",
            comments = emptyList()
        )
    )

    val fivePostsResponse: List<PostResponse> get() = fivePosts.map { it.toResponse() }

    private fun Post.toResponse() = PostResponse(
        id = this.id,
        userId = this.user.id,
        title = this.title,
        body = this.body
    )

}