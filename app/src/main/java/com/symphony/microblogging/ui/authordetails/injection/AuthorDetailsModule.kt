package com.symphony.microblogging.ui.authordetails.injection

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import com.symphony.microblogging.data.remote.network.retrofit.MicroBloggingAPI
import com.symphony.microblogging.ui.authordetails.data.remote.AuthorDetailsRemoteDataSource
import com.symphony.microblogging.ui.authordetails.domain.interactor.GetAuthorPostsUseCase
import com.symphony.microblogging.ui.authordetails.domain.repository.AuthorDetailsRepository
import com.symphony.microblogging.ui.authordetails.domain.repository.AuthorDetailsRepositoryImp
import com.symphony.microblogging.ui.authordetails.presentation.view.adapter.PostsAdapter
import com.symphony.microblogging.ui.authordetails.presentation.viewmodel.AuthorDetailsViewModel
import dagger.Module
import dagger.Provides

@Module
class AuthorDetailsModule {
    @Provides
    fun provideAuthorDetailsRemoteDataSource(microBloggingAPI: MicroBloggingAPI) =
        AuthorDetailsRemoteDataSource(microBloggingAPI = microBloggingAPI)

    @Provides
    fun provideAuthorDetailsRepository(
        remoteDataSource: AuthorDetailsRemoteDataSource
    ): AuthorDetailsRepository =
        AuthorDetailsRepositoryImp(remoteDataSource)

    @Provides
    fun provideGetAuthorPostsUseCase(repository: AuthorDetailsRepository) =
        GetAuthorPostsUseCase(repository)

    @Provides
    fun provideAuthorDetailsViewModel(
        getAuthorPostsUseCase: GetAuthorPostsUseCase
    ) =
        AuthorDetailsViewModel(getAuthorPostsUseCase)

    @Provides
    fun provideLinearLayoutManager(context: Context) =
        LinearLayoutManager(context)

    @Provides
    fun provideAuthorPostsAdapter() =
        PostsAdapter()
}