package io.github.wellingtoncosta.feed.infrastructure.network.api

import io.github.wellingtoncosta.feed.infrastructure.network.entity.UserResponse

interface UserApi {

    suspend fun getUserById(userId: Long): UserResponse?

}
