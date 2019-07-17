package io.github.wellingtoncosta.feed.infrastructure.repository

import androidx.collection.LongSparseArray
import io.github.wellingtoncosta.feed.domain.entity.User
import io.github.wellingtoncosta.feed.domain.repository.UserRepository
import io.github.wellingtoncosta.feed.infrastructure.network.api.UserApi
import io.github.wellingtoncosta.feed.infrastructure.network.entity.toDomain
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class UserRepositoryImp(
    private val userApi: UserApi
) : UserRepository {

    private val userCache = LongSparseArray<User>()

    override suspend fun getUserById(userId: Long) = withContext(IO) {
        if(userCache.containsKey(userId)) {
            userCache.get(userId)
        } else {
            userApi.getUserById(userId)?.toDomain()
                .also { userCache.append(userId, it) }
        }
    }

}
