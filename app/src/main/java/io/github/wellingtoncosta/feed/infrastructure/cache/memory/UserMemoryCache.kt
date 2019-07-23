package io.github.wellingtoncosta.feed.infrastructure.cache.memory

import androidx.collection.LongSparseArray
import io.github.wellingtoncosta.feed.domain.entity.User
import io.github.wellingtoncosta.feed.infrastructure.cache.UserCache

class UserMemoryCache : UserCache {

    private val cache = LongSparseArray<User>()

    override suspend fun get(userId: Long) = cache.get(userId)

    override suspend fun put(user: User) = cache.append(user.id, user)

    override suspend fun clearAll() = cache.clear()

}
