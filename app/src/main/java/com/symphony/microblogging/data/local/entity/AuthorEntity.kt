package com.symphony.microblogging.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Author")
data class AuthorEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var authorID: Int,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "userName")
    var userName: String,
    @ColumnInfo(name = "email")
    var email: String,
    @ColumnInfo(name = "avatarUrl")
    var avatarUrl: String,
    @ColumnInfo(name = "latitude")
    var latitude: Double,
    @ColumnInfo(name = "longitude")
    var longitude: Double
)