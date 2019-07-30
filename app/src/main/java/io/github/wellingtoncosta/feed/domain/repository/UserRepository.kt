package io.github.wellingtoncosta.feed.domain.repository

import io.github.wellingtoncosta.feed.domain.entity.User

interface UserRepository {

    suspend fun getUserById(userId: Long): User

}
