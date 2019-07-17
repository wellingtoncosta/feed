package io.github.wellingtoncosta.feed.app.ui.listposts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.github.wellingtoncosta.feed.app.ui.CoroutinesViewModel
import io.github.wellingtoncosta.feed.domain.entity.Post
import io.github.wellingtoncosta.feed.domain.interactor.PostInteractor
import kotlinx.coroutines.launch

class ListPostsViewModel(
    private val interactor: PostInteractor
) : CoroutinesViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    private val _posts = MutableLiveData<List<Post>>()
    private val _error = MutableLiveData<Throwable>()

    val loading: LiveData<Boolean> get() = _loading
    val posts: LiveData<List<Post>> get() = _posts
    val error: LiveData<Throwable> get() = _error

    fun getAllPosts() {
        viewModeScope.launch {
            try {
                _loading.value = true
                _posts.value = interactor.getPosts()
            } catch (exception: Exception) {
                _error.value = exception
            } finally {
                _loading.value = false
            }
        }
    }

}
