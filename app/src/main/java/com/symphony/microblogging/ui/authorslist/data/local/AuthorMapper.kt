package com.symphony.microblogging.ui.authorslist.data.local

import com.symphony.microblogging.data.local.entity.AuthorEntity
import com.symphony.microblogging.data.remote.network.response.Address
import com.symphony.microblogging.data.remote.network.response.Author


fun AuthorEntity.mapToUI(): Author {
    return Author(
        authorID = this.authorID
        , name = this.name
        , userName = this.userName
        , email = this.email
        , avatarUrl = this.avatarUrl
        , address = Address(this.latitude, this.longitude)
    )
}

fun Author.map(): AuthorEntity {
    return AuthorEntity(
        authorID = this.authorID
        , name = this.name
        , userName = this.userName
        , email = this.email
        , avatarUrl = this.avatarUrl
        , latitude = this.address.latitude
        , longitude = this.address.longitude
    )
}