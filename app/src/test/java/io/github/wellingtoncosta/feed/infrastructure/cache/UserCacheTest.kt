package io.github.wellingtoncosta.feed.infrastructure.cache

import io.github.wellingtoncosta.feed.domain.entity.User
import io.github.wellingtoncosta.feed.infrastructure.cache.memory.UserMemoryCache
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test

class UserCacheTest {

    private val cache = UserMemoryCache()

    @Test fun `should return null when get a user from cache when it is empty`() = runBlocking {
        cache.clearAll()

        val user = cache.get(1)

        assertNull(user)
    }

    @Test fun `should return a user when get a user from cache`() = runBlocking {
        cache.clearAll()

        val user = User(id = 1L, name = "Test", username = "test", email = "test@test.com", phone = "123456789")

        cache.put(user)

        val result = cache.get(1)

        assertNotNull(result)
        assertEquals(user, result)
    }

}