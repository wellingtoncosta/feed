package io.github.wellingtoncosta.feed.repository

import io.github.wellingtoncosta.feed.domain.exception.PostNotFoundException
import io.github.wellingtoncosta.feed.domain.repository.UserRepository
import io.github.wellingtoncosta.feed.infrastructure.network.api.CommentApi
import io.github.wellingtoncosta.feed.infrastructure.network.api.PostApi
import io.github.wellingtoncosta.feed.infrastructure.repository.PostRepositoryImpl
import io.github.wellingtoncosta.feed.mock.PostMock.fivePostsResponse
import io.github.wellingtoncosta.feed.mock.PostMock.onePost
import io.github.wellingtoncosta.feed.mock.PostMock.onePostResponse
import io.github.wellingtoncosta.feed.mock.UserMock.oneUser
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class PostRepositoryTest {

    private val userRepository = mockk<UserRepository>()
    private val postApi = mockk<PostApi>()
    private val commentApi = mockk<CommentApi>()
    private val postRepository = PostRepositoryImpl(userRepository, postApi, commentApi)

    @Test fun `should return a empty list of posts`() = runBlocking {
        coEvery { postApi.getAllPosts() } returns emptyList()

        val result = postRepository.getAllPosts()

        assertEquals(true, result.isEmpty())

        coVerify { postApi.getAllPosts() }

        coVerify(exactly = 0) { userRepository.getUserById(any()) }
    }

    @Test fun `should return a list containing posts`() = runBlocking {
        coEvery { postApi.getAllPosts() } returns fivePostsResponse

        coEvery { userRepository.getUserById(any()) } returns oneUser

        val result = postRepository.getAllPosts()

        coVerify { postApi.getAllPosts() }

        coVerify { userRepository.getUserById(any()) }

        assertEquals(5, result.size)
    }

    @Test fun `should find a post by id`() = runBlocking {
        coEvery { postApi.getPostById(any()) } returns onePostResponse

        coEvery { userRepository.getUserById(any()) } returns oneUser

        coEvery { commentApi.getCommentsByPostId(any()) } returns emptyList()

        val result = postRepository.getPostById(1)

        coVerify { postApi.getPostById(any()) }

        coVerify { userRepository.getUserById(any()) }

        assertEquals(onePost, result)
    }

    @Test(expected = PostNotFoundException::class)
    fun `should throw an exception when get a post by id`() = runBlocking {
        coEvery { postApi.getPostById(any()) } returns null

        postRepository.getPostById(1)

        coVerify { postApi.getPostById(any()) }
    }

}