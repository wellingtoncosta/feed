package io.github.wellingtoncosta.feed.app.ui.postdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.github.wellingtoncosta.feed.app.ui.CoroutinesViewModel
import io.github.wellingtoncosta.feed.domain.entity.Post
import io.github.wellingtoncosta.feed.domain.interactor.PostInteractor
import kotlinx.coroutines.launch

class PostDetailsViewModel(
    private val interactor: PostInteractor
) : CoroutinesViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    private val _post = MutableLiveData<Post>()
    private val _error = MutableLiveData<Throwable?>()

    val loading: LiveData<Boolean> get() = _loading
    val post: LiveData<Post> get() = _post
    val error: LiveData<Throwable?> get() = _error

    fun getPost(postId: Long) {
        viewModeScope.launch {
            try {
                _loading.value = true
                _post.value = interactor.getPostById(postId)
                _error.value = null
            } catch (exception: Exception) {
                _error.value = exception
            } finally {
                _loading.value = false
            }
        }
    }

}
