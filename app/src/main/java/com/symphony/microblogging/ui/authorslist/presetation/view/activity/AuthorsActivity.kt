package com.symphony.microblogging.ui.authorslist.presetation.view.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.symphony.microblogging.R
import com.symphony.microblogging.base.presentation.view.extension.setVisible
import com.symphony.microblogging.base.presentation.view.extension.showSnack
import com.symphony.microblogging.base.presentation.viewmodel.ViewModelFactory
import com.symphony.microblogging.data.remote.network.response.Author
import com.symphony.microblogging.ui.authordetails.presentation.view.activity.AuthorDetailsActivity
import com.symphony.microblogging.ui.authorslist.data.local.mapToUI
import com.symphony.microblogging.ui.authorslist.presetation.view.adapter.AuthorsAdapter
import com.symphony.microblogging.ui.authorslist.presetation.viewmodel.AuthorsViewModel
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_authors.*
import javax.inject.Inject

class AuthorsActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory<AuthorsViewModel>

    private val mViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(AuthorsViewModel::class.java)
    }

    @Inject
    lateinit var manager: LinearLayoutManager

    @Inject
    lateinit var adapter: AuthorsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        setContentView(R.layout.activity_authors)
        setupControllers()
        getAuthors()
    }

    private fun setupControllers() {
        setupToolbar()
        setupRecyclerView()
        observeAuthorsChange()
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.authors_list)
    }

    private fun getAuthors() {
        mViewModel.getAuthors()
    }

    private fun setupRecyclerView() {
        manager.orientation = RecyclerView.VERTICAL
        rvAuthors.layoutManager = manager
        rvAuthors.adapter = adapter
        srlAuthors.setOnRefreshListener {
            getAuthors()
        }
    }

    @SuppressLint("CheckResult")
    private fun observeAuthorsChange() {
        mViewModel.mAuthors.observe(this, Observer { authors ->
            authors?.let {
                fillData(it)
            }
        })
        mViewModel.mAuthorsObservable.observe(this,
            successObserver = Observer {
                it?.let {
                    srlAuthors.isRefreshing = false
                    llMainContent.showSnack(it)
                }
            }, commonErrorObserver = Observer {
                srlAuthors.isRefreshing = false
            }, loadingObserver = Observer {
                it?.let {
                    srlAuthors.isRefreshing = true
                }
            }, networkErrorConsumer = Observer {
                srlAuthors.isRefreshing = false
                llMainContent.showSnack(
                    getString(R.string.internet_connection),
                    Snackbar.LENGTH_LONG
                )
                mViewModel.getCachedAuthors().observe(this, Observer {
                    if (it != null) {
                        fillData(it.map { it.mapToUI() })
                    }
                })
            })

        adapter.getViewClickedObservable().subscribe {
            it?.let {
                openAuthorDetailsActivity(it)
            }
        }
    }

    private fun fillData(authors: List<Author>) {
        srlAuthors.isRefreshing = false
        rvAuthors.setVisible(authors.isNotEmpty())
        llNoData.setVisible(authors.isEmpty())
        adapter.replaceAllItems(authors.toMutableList())
    }

    private fun openAuthorDetailsActivity(author: Author) {
        val detailsIntent = Intent(this, AuthorDetailsActivity::class.java)
        detailsIntent.putExtra(AuthorDetailsActivity.EXTRA_AUTHOR, author)
        startActivity(detailsIntent)
    }
}
