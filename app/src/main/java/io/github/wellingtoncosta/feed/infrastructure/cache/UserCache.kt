package io.github.wellingtoncosta.feed.infrastructure.cache

import io.github.wellingtoncosta.feed.domain.entity.User

interface UserCache {

    suspend fun get(userId: Long): User?

    suspend fun put(user: User)

}
