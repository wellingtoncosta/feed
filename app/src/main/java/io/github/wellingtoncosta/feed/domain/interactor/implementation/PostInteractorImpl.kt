package io.github.wellingtoncosta.feed.domain.interactor.implementation

import io.github.wellingtoncosta.feed.domain.interactor.PostInteractor
import io.github.wellingtoncosta.feed.domain.repository.PostRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class PostInteractorImpl(
    private val repository: PostRepository
) : PostInteractor {

    override suspend fun getPosts() = withContext(IO) {
        repository.getAllPosts()
    }

    override suspend fun getPostById(postId: Long) = withContext(IO) {
        repository.getPostById(postId)
    }

}
