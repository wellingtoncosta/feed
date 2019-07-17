package io.github.wellingtoncosta.feed.domain.entity

data class User(
    val id: Long,
    val name: String,
    val username: String,
    val email: String,
    val phone: String
)
