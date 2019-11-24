package com.symphony.microblogging.ui.authorslist.presetation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.symphony.microblogging.base.domain.exception.MicroBloggingException
import com.symphony.microblogging.base.presentation.model.ObservableResource
import com.symphony.microblogging.base.presentation.viewmodel.BaseViewModel
import com.symphony.microblogging.data.local.entity.AuthorEntity
import com.symphony.microblogging.data.remote.network.response.Author
import com.symphony.microblogging.ui.authorslist.data.local.map
import com.symphony.microblogging.ui.authorslist.domain.interactor.GetAuthorsUseCase
import com.symphony.microblogging.ui.authorslist.domain.interactor.AddAuthorsLocalUseCase
import com.symphony.microblogging.ui.authorslist.domain.interactor.GetCachedAuthorsUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AuthorsViewModel @Inject constructor(
    private val getAuthorsUseCase: GetAuthorsUseCase,
    private val addAuthorsLocalUseCase: AddAuthorsLocalUseCase,
    private val getCachedAuthorsUseCase: GetCachedAuthorsUseCase
) : BaseViewModel() {
    private var authorsList = mutableListOf<Author>()
    val mAuthors = MutableLiveData<List<Author>>()
    val mAuthorsObservable = ObservableResource<String>()

    fun getAuthors() {
        addDisposable(getAuthorsUseCase.build(params = "")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                mAuthorsObservable.loading.postValue(true)
            }
            .doAfterTerminate {
                mAuthorsObservable.loading.postValue(false)
            }
            .subscribe({
                it?.let {
                    if (it.isNotEmpty()) {
                        authorsList = it.toMutableList()
                        mAuthors.value = authorsList
                        saveAuthors(authorsList)
                    }
                }
            }, {
                (it as? MicroBloggingException).let {
                    mAuthorsObservable.error.value = it
                }
            })
        )
    }

    fun getCachedAuthors(): LiveData<List<AuthorEntity>> {
        return getCachedAuthorsUseCase.build()
    }

    private fun saveAuthors(authors: MutableList<Author>) {
        if (authors.isNotEmpty()) {
            saveAuthorsInDB(authors.map { it.map() })
        } else {
            val message = "No authors found"
            mAuthorsObservable.value = message
        }
    }

    private fun saveAuthorsInDB(authors: List<AuthorEntity>) {
        addDisposable(addAuthorsLocalUseCase.build(authors)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                val message = "Authors have been saved successfully"
                mAuthorsObservable.value = message
            }, {
                val message = "Unexpected Error"
                mAuthorsObservable.value = message
            }
            ))
    }
}