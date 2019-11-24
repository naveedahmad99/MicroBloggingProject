package com.symphony.microblogging.ui.postcomments.data.remote

import com.symphony.microblogging.data.remote.network.response.Comment
import com.symphony.microblogging.data.remote.network.retrofit.MicroBloggingAPI
import io.reactivex.Single
import javax.inject.Inject

class PostCommentsRemoteDataSource @Inject constructor(private val microBloggingAPI: MicroBloggingAPI) {
    fun getPostComments(postId: Int, page: Int, limit: Int, sort: String, order: String): Single<List<Comment>> =
        microBloggingAPI.loadPostComments(postId = postId, page = page, limit = limit, sort = sort, order = order)
}