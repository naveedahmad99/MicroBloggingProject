package com.symphony.microblogging.ui.postcomments.domain.repository

import com.symphony.microblogging.data.remote.network.response.Comment
import io.reactivex.Single

interface PostCommentsRepository {
    fun getPostComments(
        postId: Int,
        page: Int,
        limit: Int,
        sort: String,
        order: String
    ): Single<List<Comment>>
}