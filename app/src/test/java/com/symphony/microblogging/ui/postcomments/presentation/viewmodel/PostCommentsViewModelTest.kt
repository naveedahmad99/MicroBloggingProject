package com.symphony.microblogging.ui.postcomments.presentation.viewmodel

import com.symphony.microblogging.data.remote.network.response.Comment
import com.symphony.microblogging.ui.postcomments.domain.interactor.GetPostCommentsUseCase
import com.symphony.microblogging.ui.postcomments.domain.repository.PostCommentsRepository
import com.symphony.microblogging.util.Constants
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.*

class PostCommentsViewModelTest {
    @Mock
    private lateinit var postCommentsRepository: PostCommentsRepository

    @Mock
    private lateinit var getPostCommentsUseCase: GetPostCommentsUseCase

    private lateinit var postCommentsViewModel: PostCommentsViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        postCommentsViewModel =
            PostCommentsViewModel(getPostCommentsUseCase)
    }

    @Test
    fun checkResponseOfCommentsNotEmpty() {
        val comments = listOf(
            Comment(
                id = 1,
                body = "test1",
                postId = 1,
                avatarUrl = "https://s3.amazonaws.com/uifaces/faces/twitter/tgerken/128.jpg",
                date = Date(),
                email = "test1@gmail.com",
                userName = "test1"
            )
        )
        Mockito.`when`(
            postCommentsRepository.getPostComments(
                postId = 1,
                page = 1,
                limit = Constants.PAGE_SIZE,
                order = "",
                sort = ""
            )
        )
            .thenReturn(Single.just(comments))
        val response = postCommentsRepository.getPostComments(
            postId = 1,
            page = 1,
            limit = Constants.PAGE_SIZE,
            order = "",
            sort = ""
        )
        response.test()
            .assertValue { it.isNotEmpty() }
    }

    @Test
    fun checkResponseOfCommentsName() {
        val comments = listOf(
            Comment(
                id = 1,
                body = "test1",
                postId = 1,
                avatarUrl = "https://s3.amazonaws.com/uifaces/faces/twitter/tgerken/128.jpg",
                date = Date(),
                email = "test1@gmail.com",
                userName = "test1"
            )
        )
        Mockito.`when`(
            postCommentsRepository.getPostComments(
                postId = 1,
                page = 1,
                limit = Constants.PAGE_SIZE,
                order = "",
                sort = ""
            )
        )
            .thenReturn(Single.just(comments))
        val response = postCommentsRepository.getPostComments(
            postId = 1,
            page = 1,
            limit = Constants.PAGE_SIZE,
            order = "",
            sort = ""
        )
        for (i in 0 until response.blockingGet().size) {
            response.test()
                .assertValue { it[i].body == "test" + (i + 1) }
        }
    }
}