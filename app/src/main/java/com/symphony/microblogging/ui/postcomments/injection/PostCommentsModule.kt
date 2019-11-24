package com.symphony.microblogging.ui.postcomments.injection

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import com.symphony.microblogging.data.remote.network.retrofit.MicroBloggingAPI
import com.symphony.microblogging.ui.postcomments.data.remote.PostCommentsRemoteDataSource
import com.symphony.microblogging.ui.postcomments.domain.interactor.GetPostCommentsUseCase
import com.symphony.microblogging.ui.postcomments.domain.repository.PostCommentsRepository
import com.symphony.microblogging.ui.postcomments.domain.repository.PostCommentsRepositoryImp
import com.symphony.microblogging.ui.postcomments.presentation.view.adapter.CommentsAdapter
import com.symphony.microblogging.ui.postcomments.presentation.viewmodel.PostCommentsViewModel
import dagger.Module
import dagger.Provides

@Module
class PostCommentsModule {
    @Provides
    fun providePostCommentsRemoteDataSource(microBloggingAPI: MicroBloggingAPI) =
        PostCommentsRemoteDataSource(microBloggingAPI = microBloggingAPI)

    @Provides
    fun providePostCommentsRepository(
        remoteDataSource: PostCommentsRemoteDataSource
    ): PostCommentsRepository =
        PostCommentsRepositoryImp(remoteDataSource)

    @Provides
    fun provideGetPostCommentsUseCase(repository: PostCommentsRepository) =
        GetPostCommentsUseCase(repository)

    @Provides
    fun providePostCommentsViewModel(
        getPostCommentsUseCase: GetPostCommentsUseCase
    ) =
        PostCommentsViewModel(getPostCommentsUseCase)

    @Provides
    fun provideLinearLayoutManager(context: Context) =
        LinearLayoutManager(context)

    @Provides
    fun providePostCommentsAdapter() =
        CommentsAdapter()
}