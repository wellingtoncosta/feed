package io.github.wellingtoncosta.feed.app.ui.listposts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.github.wellingtoncosta.feed.app.ui.CoroutinesViewModel
import io.github.wellingtoncosta.feed.domain.entity.Post
import io.github.wellingtoncosta.feed.domain.interactor.PostInteractor

class ListPostsViewModel(
    private val interactor: PostInteractor
) : CoroutinesViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    private val _posts = MutableLiveData<List<Post>>()
    private val _error = MutableLiveData<Throwable>()

    val loading: LiveData<Boolean> get() = _loading
    val posts: LiveData<List<Post>> get() = _posts
    val error: LiveData<Throwable> get() = _error

    init { getAllPosts() }

    fun getAllPosts() {
        launch {
            try {
                _loading.value = true
                _posts.value = interactor.getAllPosts()
            } catch (exception: Exception) {
                _error.value = exception
            } finally {
                _loading.value = false
            }
        }
    }

}
