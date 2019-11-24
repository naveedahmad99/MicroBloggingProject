package com.symphony.microblogging.data.remote.network.response

import com.google.gson.annotations.SerializedName
import java.util.*

data class Comment(
    @SerializedName("id")
    var id: Int,
    @SerializedName("date")
    var date: Date,
    @SerializedName("body")
    var body: String,
    @SerializedName("userName")
    var userName: String,
    @SerializedName("email")
    var email: String,
    @SerializedName("avatarUrl")
    var avatarUrl: String,
    @SerializedName("postId")
    var postId: Int
)

data class CommentsParams(
    var postId: Int,
    var page: Int,
    var limit: Int,
    var sort: String,
    var order: String
)