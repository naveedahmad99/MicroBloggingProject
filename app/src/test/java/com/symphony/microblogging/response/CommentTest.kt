package com.symphony.microblogging.data.remote.network.response

import com.google.gson.Gson
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.util.*

class CommentTest {
    private lateinit var json: String
    private lateinit var comment: Comment

    @Before
    fun setUp() {
        json ="{\"id\": 46914,\n" +
                "\"date\": \"2017-12-30T22:52:15.164Z\",\n" +
                "\"body\": \"Ducimus optio molestiae recusandae dolor quidem non velit cum et. Harum corporis qui reprehenderit. Quo eligendi est et explicabo doloribus maxime.\",\n" +
                "\"userName\": \"Vaughn_OHara89\",\n" +
                "\"email\": \"don.kris@hotmail.com\",\n" +
                "\"avatarUrl\": \"https://s3.amazonaws.com/uifaces/faces/twitter/darylws/128.jpg\",\n" +
                "\"postId\": 1914\n" +
                "}"
            comment = Gson().fromJson(json, Comment::class.java)
    }

    @Test
    fun checkRightPostDate() {
        Assert.assertTrue(comment.date.before(Date()))
    }

    @Test
    fun checkBodyIfEmpty() {
        Assert.assertFalse(comment.body.isEmpty())
    }

    @Test
    fun checkUserNameIfEmpty() {
        Assert.assertFalse(comment.userName.isEmpty())
    }

    @Test
    fun checkEmailIfEmpty() {
        Assert.assertFalse(comment.email.isEmpty())
    }

    @Test
    fun checkAvatarUrlIfEmpty() {
        Assert.assertFalse(comment.avatarUrl.isEmpty())
    }
}