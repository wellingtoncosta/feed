package io.github.wellingtoncosta.feed.repository

import io.github.wellingtoncosta.feed.domain.exception.UserNotFoundException
import io.github.wellingtoncosta.feed.infrastructure.cache.UserCache
import io.github.wellingtoncosta.feed.infrastructure.network.api.UserApi
import io.github.wellingtoncosta.feed.infrastructure.repository.UserRepositoryImp
import io.github.wellingtoncosta.feed.mock.UserMock.oneUser
import io.github.wellingtoncosta.feed.mock.UserMock.oneUserResponse
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifySequence
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class UserRepositoryTest {

    private val cache = mockk<UserCache>()
    private val api = mockk<UserApi>()
    private val repository = UserRepositoryImp(cache, api)

    @Test fun `should return a user by id from api and cache it`() = runBlocking {
        coEvery { cache.get(any()) } returns null

        coEvery { cache.put(any()) } just Runs

        coEvery { api.getUserById(any()) } returns oneUserResponse

        val result = repository.getUserById(1)

        coVerifySequence {

            cache.get(any())

            api.getUserById(any())

            cache.put(any())

        }

        assertEquals(oneUser, result)
    }

    @Test fun `should return a user by id from cache`() = runBlocking {
        coEvery { cache.get(any()) } returns oneUser

        val result = repository.getUserById(1)

        coVerify { cache.get(any()) }

        coVerify(exactly = 0) { api.getUserById(any()) }

        coVerify(exactly = 0) { cache.put(any()) }

        assertEquals(oneUser, result)
    }

    @Test(expected = UserNotFoundException::class)
    fun `should throw an exception when get a user by id`() = runBlocking {
        coEvery { cache.get(any()) } returns null

        coEvery { api.getUserById(any()) } returns null

        repository.getUserById(1)

        coVerify { cache.get(any()) }

        coVerify { api.getUserById(any()) }

        coVerify(exactly = 0) { cache.put(any()) }

    }

}
