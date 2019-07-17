package io.github.wellingtoncosta.feed.app.ui.listposts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.github.wellingtoncosta.feed.R
import io.github.wellingtoncosta.feed.databinding.ListPostsItemBinding
import io.github.wellingtoncosta.feed.domain.entity.Post

class ListPostsAdapter(
    private val onItemClick: (Long) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val posts = mutableListOf<Post>()

    fun updatePosts(posts: List<Post>) {
        with(this.posts) {
            clear()
            addAll(posts)
        }

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ListPostsViewHolder(LayoutInflater
            .from(parent.context)
            .inflate(R.layout.list_posts_item, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val post = posts[position]
        val binding = (holder as ListPostsViewHolder).binding

        binding.post = post
        binding.constraintLayout.setOnClickListener { onItemClick(post.id) }
    }

    override fun getItemCount() = posts.size

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        super.onViewRecycled(holder)

        (holder as ListPostsViewHolder)
            .binding.constraintLayout.setOnClickListener(null)
    }

}

private class ListPostsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val binding: ListPostsItemBinding = ListPostsItemBinding.bind(view)

}
