package io.github.wellingtoncosta.feed.app.ui.viewmodel

import androidx.lifecycle.Observer
import io.github.wellingtoncosta.feed.app.ui.postdetails.PostDetailsViewModel
import io.github.wellingtoncosta.feed.domain.entity.Post
import io.github.wellingtoncosta.feed.domain.interactor.PostInteractor
import io.github.wellingtoncosta.feed.mock.PostMock.onePost
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test

class PostDetailsViewModelTest : BaseViewModelTest() {

    private val interactor = mockk<PostInteractor>()

    private val loadingObserver = mockk<Observer<Boolean>>()

    private val postObserver = mockk<Observer<Post>>()

    private val errorObserver = mockk<Observer<Throwable?>>()

    private val viewModel = PostDetailsViewModel(interactor)

    @ExperimentalCoroutinesApi
    @Test fun `no interactions`() = runBlockingTest {
        every { postObserver.onChanged(any()) } just Runs

        viewModel.post.observeForever(postObserver)

        coVerify(exactly = 0) { interactor.getPostById(1) }
    }

    @ExperimentalCoroutinesApi
    @Test fun `get post by id with success`() = runBlockingTest {
        coEvery { interactor.getPostById(any()) } returns onePost

        every { loadingObserver.onChanged(any()) } just Runs

        every { postObserver.onChanged(any()) } just Runs

        viewModel.loading.observeForever(loadingObserver)

        viewModel.post.observeForever(postObserver)

        viewModel.getPost(1)

        coVerify { interactor.getPostById(1) }

        verify(exactly = 2) { loadingObserver.onChanged(any()) }

        verify { postObserver.onChanged(any()) }
    }

    @ExperimentalCoroutinesApi
    @Test fun `throws exception when get posts`() = runBlockingTest {
        coEvery { interactor.getPostById(any()) } throws Exception()

        every { loadingObserver.onChanged(any()) } just Runs

        every { postObserver.onChanged(any()) } just Runs

        every { errorObserver.onChanged(any()) } just Runs

        viewModel.loading.observeForever(loadingObserver)

        viewModel.post.observeForever(postObserver)

        viewModel.error.observeForever(errorObserver)

        viewModel.getPost(1)

        coVerify { interactor.getPostById(1) }

        verify(exactly = 2) { loadingObserver.onChanged(any()) }

        verify(exactly = 0) { postObserver.onChanged(any()) }

        verify { errorObserver.onChanged(any()) }
    }

}
