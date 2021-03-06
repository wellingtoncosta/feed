package io.github.wellingtoncosta.feed.app.ui.viewmodel

import androidx.lifecycle.Observer
import io.github.wellingtoncosta.feed.app.ui.listposts.ListPostsViewModel
import io.github.wellingtoncosta.feed.domain.entity.Post
import io.github.wellingtoncosta.feed.domain.interactor.PostInteractor
import io.github.wellingtoncosta.feed.mock.PostMock.fivePosts
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test

class ListPostsViewModelTest : BaseViewModelTest() {

    private val interactor = mockk<PostInteractor>()

    private val loadingObserver = mockk<Observer<Boolean>>()

    private val postsObserver = mockk<Observer<List<Post>>>()

    private val errorObserver = mockk<Observer<Throwable>>()

    private val viewModel = ListPostsViewModel(interactor = interactor)

    @ExperimentalCoroutinesApi
    @Test fun `no interactions`() = runBlockingTest {
        every { postsObserver.onChanged(any()) } just Runs

        viewModel.posts.observeForever(postsObserver)

        coVerify(exactly = 0) { interactor.getAllPosts() }
    }

    @ExperimentalCoroutinesApi
    @Test fun `get posts with success`() = runBlockingTest {
        coEvery { interactor.getAllPosts() } returns fivePosts

        every { loadingObserver.onChanged(any()) } just Runs

        every { postsObserver.onChanged(any()) } just Runs

        viewModel.loading.observeForever(loadingObserver)

        viewModel.posts.observeForever(postsObserver)

        viewModel.getAllPosts()

        coVerify { interactor.getAllPosts() }

        verify(exactly = 2) { loadingObserver.onChanged(any()) }

        verify { postsObserver.onChanged(any()) }
    }

    @ExperimentalCoroutinesApi
    @Test fun `throws exception when get posts`() = runBlockingTest {
        coEvery { interactor.getAllPosts() } throws Exception()

        every { loadingObserver.onChanged(any()) } just Runs

        every { postsObserver.onChanged(any()) } just Runs

        every { errorObserver.onChanged(any()) } just Runs

        viewModel.loading.observeForever(loadingObserver)

        viewModel.posts.observeForever(postsObserver)

        viewModel.error.observeForever(errorObserver)

        viewModel.getAllPosts()

        coVerify { interactor.getAllPosts() }

        verify(exactly = 2) { loadingObserver.onChanged(any()) }

        verify(exactly = 0) { postsObserver.onChanged(any()) }

        verify { errorObserver.onChanged(any()) }
    }

}