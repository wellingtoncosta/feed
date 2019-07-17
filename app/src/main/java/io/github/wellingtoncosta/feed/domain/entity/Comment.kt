package io.github.wellingtoncosta.feed.domain.entity

data class Comment(
    val id: Long,
    val name: String,
    val email: String,
    val body: String
)
