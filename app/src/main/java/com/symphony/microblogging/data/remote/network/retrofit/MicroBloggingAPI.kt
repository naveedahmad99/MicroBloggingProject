package com.symphony.microblogging.data.remote.network.retrofit

import com.symphony.microblogging.data.remote.network.response.Author
import com.symphony.microblogging.data.remote.network.response.Comment
import com.symphony.microblogging.data.remote.network.response.Post
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MicroBloggingAPI {
    @GET("authors")
    fun loadAuthors(): Single<List<Author>>

    @GET("posts")
    fun loadAuthorPosts(
        @Query("authorId") authorId: Int, @Query("_page") page: Int, @Query("_limit") limit: Int
    ): Single<List<Post>>

    @GET("comments")
    fun loadPostComments(
        @Query("postId") postId: Int, @Query("_page") page: Int, @Query("_limit") limit: Int
        , @Query("_sort") sort: String, @Query("_order") order: String
    ): Single<List<Comment>>
}