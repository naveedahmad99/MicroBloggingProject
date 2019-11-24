package com.symphony.microblogging.ui.authordetails.domain.interactor

import com.symphony.microblogging.base.domain.interactor.ListUseCase
import com.symphony.microblogging.data.remote.network.response.Post
import com.symphony.microblogging.data.remote.network.response.PostsParams
import com.symphony.microblogging.ui.authordetails.domain.repository.AuthorDetailsRepository
import io.reactivex.Single
import javax.inject.Inject

class GetAuthorPostsUseCase @Inject constructor(private val repository: AuthorDetailsRepository) :
    ListUseCase<PostsParams, Post>() {
    override fun build(params: PostsParams): Single<List<Post>> =
        repository.getAuthorPosts(
            authorId = params.authorId,
            page = params.page,
            limit = params.limit
        )
}