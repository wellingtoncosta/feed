package io.github.wellingtoncosta.feed.infrastructure.repository

import io.github.wellingtoncosta.feed.domain.exception.PostNotFoundException
import io.github.wellingtoncosta.feed.domain.repository.PostRepository
import io.github.wellingtoncosta.feed.domain.repository.UserRepository
import io.github.wellingtoncosta.feed.infrastructure.extension.asyncMap
import io.github.wellingtoncosta.feed.infrastructure.network.api.CommentApi
import io.github.wellingtoncosta.feed.infrastructure.network.api.PostApi
import io.github.wellingtoncosta.feed.infrastructure.network.entity.toDomain
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class PostRepositoryImpl(
    private val userRepository: UserRepository,
    private val postApi: PostApi,
    private val commentApi: CommentApi
) : PostRepository {

    override suspend fun getAllPosts() = withContext(IO) {
        postApi.getAllPosts()
            .asyncMap { it.toDomain(userRepository.getUserById(it.userId)) }
            .sortedByDescending { it.id }
    }

    override suspend fun getPostById(postId: Long) = withContext(IO) {
        postApi.getPostById(postId)?.let { response ->
            val user = async {
                userRepository.getUserById(response.userId)
            }

            val comments = async {
                commentApi.getCommentsByPostId(response.id).map { it.toDomain() }
            }

            response.toDomain(user.await(), comments.await())
        } ?: throw PostNotFoundException()
    }

}
