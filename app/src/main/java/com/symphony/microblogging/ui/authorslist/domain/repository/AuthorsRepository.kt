package com.symphony.microblogging.ui.authorslist.domain.repository

import androidx.lifecycle.LiveData
import com.symphony.microblogging.data.local.entity.AuthorEntity
import com.symphony.microblogging.data.remote.network.response.Author
import io.reactivex.Single

interface AuthorsRepository {
    fun getAuthors(): Single<List<Author>>

    fun insertAuthors(authors: List<AuthorEntity>): Single<Boolean>

    fun getCachedAuthors(): LiveData<List<AuthorEntity>>
}