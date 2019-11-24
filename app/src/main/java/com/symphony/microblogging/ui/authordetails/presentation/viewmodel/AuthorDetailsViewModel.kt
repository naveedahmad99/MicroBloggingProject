package com.symphony.microblogging.ui.authordetails.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import com.symphony.microblogging.base.domain.exception.MicroBloggingException
import com.symphony.microblogging.base.presentation.model.ObservableResource
import com.symphony.microblogging.base.presentation.viewmodel.BaseViewModel
import com.symphony.microblogging.data.remote.network.response.Post
import com.symphony.microblogging.data.remote.network.response.PostsParams
import com.symphony.microblogging.ui.authordetails.domain.interactor.GetAuthorPostsUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AuthorDetailsViewModel @Inject constructor(
    private val getAuthorPostsUseCase: GetAuthorPostsUseCase
) : BaseViewModel() {
    private var postsList = mutableListOf<Post>()
    val mPosts = MutableLiveData<List<Post>>()
    val mPostsObservable = ObservableResource<String>()

    fun getAuthorPosts(postsParams: PostsParams) {
        addDisposable(getAuthorPostsUseCase.build(params = postsParams)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                mPostsObservable.loading.postValue(true)
            }
            .doAfterTerminate {
                mPostsObservable.loading.postValue(false)
            }
            .subscribe({
                it?.let {
                    postsList = it.toMutableList()
                    mPosts.value = postsList
                }
            }, {
                (it as? MicroBloggingException).let {
                    mPostsObservable.error.value = it
                }
            })
        )
    }
}
