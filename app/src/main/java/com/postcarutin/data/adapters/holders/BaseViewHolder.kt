package com.postcarutin.data.adapters.holders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.postcarutin.data.Post
import com.postcarutin.data.adapters.PostRecyclerAdapter

abstract class BaseViewHolder(val adapter: PostRecyclerAdapter, view: View) :
    RecyclerView.ViewHolder(view) {
    abstract fun bind(post: Post)
}