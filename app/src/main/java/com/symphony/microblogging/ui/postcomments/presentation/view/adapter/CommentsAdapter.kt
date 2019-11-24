package com.symphony.microblogging.ui.postcomments.presentation.view.adapter

import android.nfc.tech.MifareUltralight
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.symphony.microblogging.base.presentation.view.adapter.BaseRecyclerAdapter
import com.symphony.microblogging.data.remote.network.response.Comment
import com.symphony.microblogging.databinding.ItemCommentBinding

class CommentsAdapter : BaseRecyclerAdapter<Comment>() {

    private lateinit var binding: ItemCommentBinding

    override fun getAdapterPageSize(): Int {
        return MifareUltralight.PAGE_SIZE
    }

    override fun mainItemView(parent: ViewGroup): View {
        binding = ItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return binding.root
    }

    override fun mainItemViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        binding = ItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommentsViewHolder(binding)
    }

    override fun onBindMainViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CommentsViewHolder) {
            holder.bind(getItems()[position])
        }
    }

    private class CommentsViewHolder(val binding: ItemCommentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Comment) {
            binding.item = item
            binding.executePendingBindings()
        }
    }
}