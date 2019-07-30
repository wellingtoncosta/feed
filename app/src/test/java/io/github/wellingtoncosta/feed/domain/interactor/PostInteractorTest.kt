package io.github.wellingtoncosta.feed.domain.interactor

import io.github.wellingtoncosta.feed.domain.exception.PostNotFoundException
import io.github.wellingtoncosta.feed.domain.interactor.implementation.PostInteractorImpl
import io.github.wellingtoncosta.feed.domain.repository.PostRepository
import io.github.wellingtoncosta.feed.mock.PostMock.fivePosts
import io.github.wellingtoncosta.feed.mock.PostMock.onePost
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class PostInteractorTest {

    private val repository = mockk<PostRepository>()
    private val interactor = PostInteractorImpl(repository)

    @Test fun `should return an empty list of posts`() = runBlocking {
        coEvery { repository.getAllPosts() } returns emptyList()

        val result = interactor.getAllPosts()

        coVerify { repository.getAllPosts() }

        assertEquals(0, result.size)
    }

    @Test fun `should return a list containing posts`() = runBlocking {
        coEvery { repository.getAllPosts() } returns fivePosts

        val result = interactor.getAllPosts()

        coVerify { repository.getAllPosts() }

        assertEquals(fivePosts, result)
    }

    @Test fun `should find a post by id`() = runBlocking {
        coEvery { repository.getPostById(any()) } returns onePost

        val result = interactor.getPostById(1)

        coVerify { repository.getPostById(any()) }

        assertEquals(onePost, result)
    }

    @Test(expected = PostNotFoundException::class)
    fun `shouldn't find a post by id`() = runBlocking {
        coEvery { repository.getPostById(any()) } throws PostNotFoundException()

        interactor.getPostById(1)

        coVerify { repository.getPostById(any()) }
    }

}