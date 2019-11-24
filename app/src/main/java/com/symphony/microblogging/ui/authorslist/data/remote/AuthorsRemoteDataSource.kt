package com.symphony.microblogging.ui.authorslist.data.remote

import com.symphony.microblogging.data.remote.network.response.Author
import com.symphony.microblogging.data.remote.network.retrofit.MicroBloggingAPI
import io.reactivex.Single
import javax.inject.Inject

class AuthorsRemoteDataSource @Inject constructor(private val microBloggingAPI: MicroBloggingAPI) {

    fun getAuthors(): Single<List<Author>> =
        microBloggingAPI.loadAuthors()
}