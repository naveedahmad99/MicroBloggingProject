package com.symphony.microblogging.data.remote.network.response

import android.os.Parcelable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.google.gson.annotations.SerializedName
import com.symphony.microblogging.R
import com.symphony.microblogging.base.presentation.view.extension.loadFromUrl
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Author(
    @SerializedName("id")
    var authorID: Int,
    @SerializedName("name")
    var name: String,
    @SerializedName("userName")
    var userName: String,
    @SerializedName("email")
    var email: String,
    @SerializedName("avatarUrl")
    var avatarUrl: String,
    @SerializedName("address")
    var address: Address
) : Parcelable

@Parcelize
data class Address(
    @SerializedName("latitude")
    var latitude: Double,
    @SerializedName("longitude")
    var longitude: Double
) : Parcelable

@BindingAdapter("bind:backgroundImageUrl")
fun loadBackgroundImage(view: ImageView, imageUrl: String?) {
    imageUrl?.let { view.loadFromUrl(url = it, placeholder = R.color.colorPrimary, isRounded = true) }
}