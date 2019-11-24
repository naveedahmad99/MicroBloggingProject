package com.symphony.microblogging.ui.authordetails.presentation.view.adapter

import android.nfc.tech.MifareUltralight
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.symphony.microblogging.base.presentation.view.adapter.BaseRecyclerAdapter
import com.symphony.microblogging.data.remote.network.response.Post
import com.symphony.microblogging.databinding.ItemPostBinding
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class PostsAdapter : BaseRecyclerAdapter<Post>() {

    private val mViewClickSubject = PublishSubject.create<Post>()
    private lateinit var binding: ItemPostBinding
    fun getViewClickedObservable(): Observable<Post> {
        return mViewClickSubject
    }

    override fun getAdapterPageSize(): Int {
        return MifareUltralight.PAGE_SIZE
    }

    override fun mainItemView(parent: ViewGroup): View {
        binding = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return binding.root
    }

    override fun mainItemViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        binding = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostsViewHolder(binding)
    }

    override fun onBindMainViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is PostsViewHolder) {
            holder.bind(getItems()[position])
            holder.itemView.setOnClickListener {
                mViewClickSubject.onNext(getItems()[position])
            }
        }
    }

    private class PostsViewHolder(val binding: ItemPostBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Post) {
            binding.item = item
            binding.executePendingBindings()
        }
    }
}