package com.symphony.microblogging.ui.authorslist.domain.repository

import androidx.lifecycle.LiveData
import com.symphony.microblogging.data.local.entity.AuthorEntity
import com.symphony.microblogging.data.remote.network.response.Author
import com.symphony.microblogging.ui.authorslist.data.local.AuthorsLocalDataSource
import com.symphony.microblogging.ui.authorslist.data.remote.AuthorsRemoteDataSource
import io.reactivex.Single
import javax.inject.Inject

class AuthorsRepositoryImp @Inject constructor(
    private val remoteDataSource: AuthorsRemoteDataSource,
    private val localDataSource: AuthorsLocalDataSource
) : AuthorsRepository {
    override fun getCachedAuthors(): LiveData<List<AuthorEntity>> =
        localDataSource.getCachedAuthors()

    override fun insertAuthors(authors: List<AuthorEntity>): Single<Boolean> =
        localDataSource.insertAuthors(authors = authors)

    override fun getAuthors(): Single<List<Author>> =
        remoteDataSource.getAuthors()
}