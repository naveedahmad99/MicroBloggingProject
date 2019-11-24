package com.symphony.microblogging.data.remote.network.response

import com.google.gson.Gson
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class AuthorTest {
    private lateinit var json: String
    private lateinit var author: Author

    @Before
    fun setUp() {
        json = "{\"id\": 30,\n" +
                "\"name\": \"Lela Ratke\",\n" +
                "\"userName\": \"Alysa.Armstrong\",\n" +
                "\"email\": \"carli.langworth@gmail.com\",\n" +
                "\"avatarUrl\": \"https://s3.amazonaws.com/uifaces/faces/twitter/tgerken/128.jpg\",\n" +
                "\"address\": {\n" +
                "\"latitude\": \"-11.2796\",\n" +
                "\"longitude\": \"-152.8463\"\n" +
                "}}"
        author = Gson().fromJson(json, Author::class.java)
    }


    @Test
    fun checkNameIfEmpty() {
        Assert.assertFalse(author.name.isEmpty())
    }

    @Test
    fun checkUserNameIfEmpty() {
        Assert.assertFalse(author.userName.isEmpty())
    }

    @Test
    fun checkEmailIfEmpty() {
        Assert.assertFalse(author.email.isEmpty())
    }

    @Test
    fun checkAvatarUrlIfEmpty() {
        Assert.assertFalse(author.avatarUrl.isEmpty())
    }

    @Test
    fun checkAddressLatitudeIfEmpty() {
        Assert.assertFalse(author.address.latitude.isNaN())
    }

    @Test
    fun checkAddressLongitudeIfEmpty() {
        Assert.assertFalse(author.address.longitude.isNaN())
    }
}