package io.github.wellingtoncosta.feed.app.ui.postdetails

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import io.github.wellingtoncosta.feed.R
import io.github.wellingtoncosta.feed.app.ui.extension.observe
import io.github.wellingtoncosta.feed.app.ui.listposts.ListPostsActivity.Companion.POST_ID
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import io.github.wellingtoncosta.feed.databinding.ActivityPostDetailsBinding as Binding

class PostDetailsActivity : AppCompatActivity() {

    private val binding by lazy {
        DataBindingUtil.setContentView<Binding>(this, R.layout.activity_post_details)
    }

    private val viewModel by viewModel<PostDetailsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent.extras?.let {
            if(intent.hasExtra(POST_ID)) {
                viewModel.getPost(it.getLong(POST_ID))
            }
        }

        this.let { activity ->
            binding.apply {
                lifecycleOwner = activity
                viewModel = activity.viewModel
            }
        }

        setupToolbar()

        observePost()

        observeError()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = null
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }

    private fun observePost() {
        viewModel.post.observe(this) { post ->
            Timber.d("post = $post")
        }
    }

    private fun observeError() {
        viewModel.error.observe(this) {
            Timber.e(it)
        }
    }

}
