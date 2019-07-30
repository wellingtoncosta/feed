package io.github.wellingtoncosta.feed.infrastructure.repository

import io.github.wellingtoncosta.feed.domain.exception.UserNotFoundException
import io.github.wellingtoncosta.feed.domain.repository.UserRepository
import io.github.wellingtoncosta.feed.infrastructure.cache.UserCache
import io.github.wellingtoncosta.feed.infrastructure.network.api.UserApi
import io.github.wellingtoncosta.feed.infrastructure.network.entity.toDomain
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class UserRepositoryImp(
    private val cache: UserCache,
    private val api: UserApi
) : UserRepository {

    override suspend fun getUserById(userId: Long) = withContext(IO) {
        cache.get(userId) ?: api.getUserById(userId).let { response ->
            response?.toDomain()?.also { cache.put(it) } ?: throw UserNotFoundException()
        }
    }

}
