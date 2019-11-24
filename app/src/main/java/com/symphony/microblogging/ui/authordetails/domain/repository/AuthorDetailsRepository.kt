package com.symphony.microblogging.ui.authordetails.domain.repository

import com.symphony.microblogging.data.remote.network.response.Post
import io.reactivex.Single

interface AuthorDetailsRepository {
    fun getAuthorPosts(authorId: Int, page: Int, limit: Int): Single<List<Post>>
}