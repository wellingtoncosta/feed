package io.github.wellingtoncosta.feed.app.ui.listposts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import io.github.wellingtoncosta.feed.R
import io.github.wellingtoncosta.feed.databinding.ActivityListPostsBinding as Binding
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class ListPostsActivity : AppCompatActivity() {

    private val binding by lazy {
        DataBindingUtil.setContentView<Binding>(this, R.layout.activity_list_posts)
    }

    private val viewModel by viewModel<ListPostsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activity = this

        binding.apply {
            lifecycleOwner = activity
            viewModel = activity.viewModel
            recyclerView.adapter = ListPostsAdapter(::goToPostDetails)
        }

        observePosts()

        observeError()
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        viewModel.getAllPosts()
    }

    private fun observePosts() {
        viewModel.posts.observe(this, Observer { posts ->
            // Timber.d("posts = $posts")

            binding.recyclerView.adapter?.let {
                (it as ListPostsAdapter).updatePosts(posts)
            }
        })
    }

    private fun observeError() {
        viewModel.error.observe(this, Observer {
            Timber.e("error = $it")

            Toast.makeText(
                this@ListPostsActivity,
                R.string.load_posts_error,
                Toast.LENGTH_LONG
            ).show()
        })
    }

    private fun goToPostDetails(postId: Long) {
        Timber.d("postId = $postId")
    }

}
