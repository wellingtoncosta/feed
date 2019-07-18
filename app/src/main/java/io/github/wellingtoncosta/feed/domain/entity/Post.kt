package io.github.wellingtoncosta.feed.domain.entity

data class Post(
    val id: Long,
    val user: User,
    val title: String,
    val body: String,
    val comments: List<Comment>
)
