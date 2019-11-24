package com.symphony.microblogging.data.remote.network.response

import com.google.gson.Gson
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.util.*

class PostTest {
    private lateinit var json: String
    private lateinit var post: Post

    @Before
    fun setUp() {
        json =
            "{\"id\": 1262,\n" +
                    "\"date\": \"2017-05-18T02:08:18.033Z\",\n" +
                    "\"title\": \"Totam quibusdam velit dolorem aut quos ducimus voluptatem pariatur aut.\",\n" +
                    "\"body\": \"Maiores excepturi at dolorem aliquid. Aliquid nihil similique tenetur doloribus a repudiandae. Maxime incidunt qui autem consectetur voluptates. Voluptates voluptatibus soluta perspiciatis doloremque dolor inventore voluptatem tempora possimus. Corrupti totam quisquam. Laborum quia natus et.\",\n" +
                    "\"imageUrl\": \"https://picsum.photos/id/621/640/480\",\n" +
                    "\"authorId\": 12\n" +
                    "}"
        post = Gson().fromJson(json, Post::class.java)
    }

    @Test
    fun checkRightPostDate() {
        Assert.assertTrue(post.date.before(Date()))
    }

    @Test
    fun checkTitleIfEmpty() {
        Assert.assertFalse(post.title.isEmpty())
    }

    @Test
    fun checkBodyIfEmpty() {
        Assert.assertFalse(post.body.isEmpty())
    }

    @Test
    fun checkImageUrlIfEmpty() {
        Assert.assertFalse(post.imageUrl.isEmpty())
    }
}