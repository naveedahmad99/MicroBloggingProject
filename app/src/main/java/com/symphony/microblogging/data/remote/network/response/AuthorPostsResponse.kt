package com.symphony.microblogging.data.remote.network.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Post(
    @SerializedName("id")
    var id: Int,
    @SerializedName("date")
    var date: Date,
    @SerializedName("title")
    var title: String,
    @SerializedName("body")
    var body: String,
    @SerializedName("authorId")
    var authorId: Int,
    @SerializedName("imageUrl")
    var imageUrl: String
) : Parcelable

data class PostsParams(
    var authorId: Int,
    var page: Int,
    var limit: Int
)