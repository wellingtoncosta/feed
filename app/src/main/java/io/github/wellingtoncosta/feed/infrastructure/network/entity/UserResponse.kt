package io.github.wellingtoncosta.feed.infrastructure.network.entity

import io.github.wellingtoncosta.feed.domain.entity.User
import kotlinx.serialization.Serializable

@Serializable data class UserResponse(
    val id: Long,
    val name: String,
    val username: String,
    val email: String,
    val phone: String
)

fun UserResponse.toDomain() = User(
    id = id,
    name = name,
    username = username,
    email = email,
    phone = phone
)
