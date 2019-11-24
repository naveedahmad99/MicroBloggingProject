package com.symphony.microblogging.ui.postcomments.domain.interactor

import com.symphony.microblogging.base.domain.interactor.ListUseCase
import com.symphony.microblogging.data.remote.network.response.Comment
import com.symphony.microblogging.data.remote.network.response.CommentsParams
import com.symphony.microblogging.ui.postcomments.domain.repository.PostCommentsRepository
import io.reactivex.Single
import javax.inject.Inject

class GetPostCommentsUseCase @Inject constructor(private val repository: PostCommentsRepository) :
    ListUseCase<CommentsParams, Comment>() {
    override fun build(params: CommentsParams): Single<List<Comment>> =
        repository.getPostComments(
            postId = params.postId,
            page = params.page,
            order = params.order,
            sort = params.sort,
            limit = params.limit
        )
}