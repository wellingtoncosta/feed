package io.github.wellingtoncosta.feed.app.ui.postdetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.TextViewCompat
import androidx.recyclerview.widget.RecyclerView
import io.github.wellingtoncosta.feed.R
import io.github.wellingtoncosta.feed.databinding.ListCommentsItemBinding
import io.github.wellingtoncosta.feed.domain.entity.Comment

class ListCommentssAdapter(
    private val comments: List<Comment>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ListCommentsViewHolder(LayoutInflater
            .from(parent.context)
            .inflate(R.layout.list_comments_item, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ListCommentsViewHolder).binding.comment = comments[position]
    }

    override fun getItemCount() = comments.size

}

private class ListCommentsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val binding: ListCommentsItemBinding = ListCommentsItemBinding.bind(view).also {
        TextViewCompat.setAutoSizeTextTypeWithDefaults(it.body, TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM)
    }

}
