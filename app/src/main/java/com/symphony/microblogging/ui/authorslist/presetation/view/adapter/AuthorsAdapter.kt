package com.symphony.microblogging.ui.authorslist.presetation.view.adapter

import android.nfc.tech.MifareUltralight.PAGE_SIZE
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.symphony.microblogging.base.presentation.view.adapter.BaseRecyclerAdapter
import com.symphony.microblogging.data.remote.network.response.Author
import com.symphony.microblogging.databinding.ItemAuthorBinding
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject


class AuthorsAdapter : BaseRecyclerAdapter<Author>() {

    private val mViewClickSubject = PublishSubject.create<Author>()
    private lateinit var binding: ItemAuthorBinding

    fun getViewClickedObservable(): Observable<Author> {
        return mViewClickSubject
    }

    override fun getAdapterPageSize(): Int {
        return PAGE_SIZE
    }

    override fun mainItemView(parent: ViewGroup): View {
        binding = ItemAuthorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return binding.root
    }

    override fun mainItemViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        binding = ItemAuthorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AuthorViewHolder(binding)
    }

    override fun onBindMainViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is AuthorViewHolder) {
            holder.bind(getItems()[position])
            holder.itemView.setOnClickListener {
                mViewClickSubject.onNext(getItems()[position])
            }
        }
    }

    private class AuthorViewHolder(val binding: ItemAuthorBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Author) = with(itemView) {
            binding.item = item
            binding.executePendingBindings()
        }
    }
}