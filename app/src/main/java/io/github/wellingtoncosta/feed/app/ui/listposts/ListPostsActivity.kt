package io.github.wellingtoncosta.feed.app.ui.listposts

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import io.github.wellingtoncosta.feed.R
import io.github.wellingtoncosta.feed.app.ui.extension.observe
import io.github.wellingtoncosta.feed.app.ui.extension.startNewActivity
import io.github.wellingtoncosta.feed.app.ui.postdetails.PostDetailsActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import io.github.wellingtoncosta.feed.databinding.ActivityListPostsBinding as Binding

class ListPostsActivity : AppCompatActivity() {

    private val binding by lazy {
        DataBindingUtil.setContentView<Binding>(this, R.layout.activity_list_posts)
    }

    private val viewModel by viewModel<ListPostsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.let { activity ->
            binding.apply {
                lifecycleOwner = activity
                viewModel = activity.viewModel
                recyclerView.adapter = ListPostsAdapter(::goToPostDetails)
            }
        }

        observePosts()

        observeError()
    }

    private fun observePosts() {
        viewModel.posts.observe(this) { posts ->
            Timber.d("posts = $posts")

            binding.recyclerView.adapter?.let {
                (it as ListPostsAdapter).updatePosts(posts)
            }
        }
    }

    private fun observeError() {
        viewModel.error.observe(this) {
            Timber.e(it)

            Toast.makeText(
                this@ListPostsActivity,
                R.string.load_posts_error,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun goToPostDetails(postId: Long) {
        Timber.d("postId = $postId")

        startNewActivity(PostDetailsActivity::class) {
            putExtra(POST_ID, postId)
        }
    }

    companion object {
        const val POST_ID = "post-id"
    }

}
