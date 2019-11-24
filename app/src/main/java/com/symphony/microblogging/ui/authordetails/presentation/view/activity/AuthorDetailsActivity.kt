package com.symphony.microblogging.ui.authordetails.presentation.view.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.symphony.microblogging.R
import com.symphony.microblogging.base.presentation.view.adapter.BaseRecyclerAdapter
import com.symphony.microblogging.base.presentation.view.extension.setVisible
import com.symphony.microblogging.base.presentation.view.extension.showSnack
import com.symphony.microblogging.base.presentation.viewmodel.ViewModelFactory
import com.symphony.microblogging.data.remote.network.response.Author
import com.symphony.microblogging.data.remote.network.response.Post
import com.symphony.microblogging.data.remote.network.response.PostsParams
import com.symphony.microblogging.databinding.ActivityAuthorDetailsBinding
import com.symphony.microblogging.ui.authordetails.presentation.view.adapter.PostsAdapter
import com.symphony.microblogging.ui.authordetails.presentation.viewmodel.AuthorDetailsViewModel
import com.symphony.microblogging.ui.postcomments.presentation.view.activity.PostCommentsActivity
import com.symphony.microblogging.util.Constants
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_author_details.*
import javax.inject.Inject

class AuthorDetailsActivity : AppCompatActivity(), BaseRecyclerAdapter.OnLoadMoreListener {

    companion object {
        const val EXTRA_AUTHOR = "author"
    }

    lateinit var activityAuthorDetailsBinding: ActivityAuthorDetailsBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory<AuthorDetailsViewModel>

    private val mViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(AuthorDetailsViewModel::class.java)
    }

    @Inject
    lateinit var manager: LinearLayoutManager

    @Inject
    lateinit var adapter: PostsAdapter

    private var page = 1
    private val limit = Constants.PAGE_SIZE
    private var isLastPage = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        activityAuthorDetailsBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_author_details)
        bindAuthor()
        setupControllers()
        getAuthorPosts()
    }

    private fun bindAuthor() {
        val extras = intent.extras
        extras?.let {
            activityAuthorDetailsBinding.author = (it.getParcelable(EXTRA_AUTHOR) as? Author)!!
        }
    }

    private fun setupControllers() {
        setupRecyclerView()
        observePostsChange()
    }

    private fun getAuthorPosts() {
        srlPosts.isRefreshing = true
        mViewModel.getAuthorPosts(
            PostsParams(
                authorId = activityAuthorDetailsBinding.author!!.authorID,
                page = page,
                limit = limit
            )
        )
    }

    private fun setupRecyclerView() {
        manager.orientation = RecyclerView.VERTICAL
        rvPosts.layoutManager = manager
        adapter.addOnLoadMoreListener(this, rvPosts, manager)
        rvPosts.adapter = adapter
        srlPosts.setOnRefreshListener {
            page = 1
            getAuthorPosts()
        }
    }

    override fun onLoadMore() {
        if (!srlPosts.isRefreshing && !isLastPage) {
            getAuthorPosts()
        }
    }

    @SuppressLint("CheckResult")
    private fun observePostsChange() {
        mViewModel.mPosts.observe(this, Observer { posts ->
            posts?.let {
                fillData(it)
            }
        })
        mViewModel.mPostsObservable.observe(this,
            successObserver = Observer {
                it?.let {
                    srlPosts.isRefreshing = false
                    llMainContent.showSnack(it)
                }
            }, commonErrorObserver = Observer {
                srlPosts.isRefreshing = false
            }, loadingObserver = Observer {
                it?.let {
                }
            }, networkErrorConsumer = Observer {
                srlPosts.isRefreshing = false
                llMainContent.showSnack(
                    getString(R.string.internet_connection),
                    Snackbar.LENGTH_LONG
                )
            })

        adapter.getViewClickedObservable().subscribe {
            it?.let {
                openPostCommentsActivity(it)
            }
        }
    }

    private fun fillData(posts: List<Post>) {
        if (page == 1) {
            adapter.replaceAllItems(posts.toMutableList())
            adapter.addOnLoadMoreListener(this, rvPosts, manager)
        } else {
            adapter.addMoreItems(posts.toMutableList())
        }

        isLastPage = posts.isEmpty() || posts.size < limit

        if (!isLastPage) {
            page++
        }

        srlPosts.isRefreshing = false
        rvPosts.setVisible(adapter.data.isNotEmpty())
        llNoData.setVisible(adapter.data.isEmpty())
    }

    private fun openPostCommentsActivity(post: Post) {
        val commentsIntent = Intent(this, PostCommentsActivity::class.java)
        commentsIntent.putExtra(PostCommentsActivity.EXTRA_POST, post)
        startActivity(commentsIntent)
    }
}
