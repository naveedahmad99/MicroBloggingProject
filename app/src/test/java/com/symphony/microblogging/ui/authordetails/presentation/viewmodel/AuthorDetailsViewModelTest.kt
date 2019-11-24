package com.symphony.microblogging.ui.authordetails.presentation.viewmodel

import com.symphony.microblogging.data.remote.network.response.Post
import com.symphony.microblogging.ui.authordetails.domain.interactor.GetAuthorPostsUseCase
import com.symphony.microblogging.ui.authordetails.domain.repository.AuthorDetailsRepository
import com.symphony.microblogging.util.Constants
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.*

class AuthorDetailsViewModelTest {
    @Mock
    private lateinit var authorDetailsRepository: AuthorDetailsRepository

    @Mock
    private lateinit var getAuthorPostsUseCase: GetAuthorPostsUseCase

    private lateinit var authorDetailsViewModel: AuthorDetailsViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        authorDetailsViewModel =
            AuthorDetailsViewModel(getAuthorPostsUseCase)
    }

    @Test
    fun checkResponseOfPostsNotEmpty() {
        val comments = listOf(
            Post(
                id = 1,
                body = "test1",
                title = "test1",
                authorId = 1,
                imageUrl = "https://s3.amazonaws.com/uifaces/faces/twitter/tgerken/128.jpg",
                date = Date()
            )
        )
        Mockito.`when`(
            authorDetailsRepository.getAuthorPosts(
                authorId = 1,
                page = 1,
                limit = Constants.PAGE_SIZE
            )
        )
            .thenReturn(Single.just(comments))
        val response = authorDetailsRepository.getAuthorPosts(
            authorId = 1,
            page = 1,
            limit = Constants.PAGE_SIZE
        )
        response.test()
            .assertValue { it.isNotEmpty() }
    }

    @Test
    fun checkResponseOfPostsName() {
        val comments = listOf(
            Post(
                id = 1,
                body = "test1",
                title = "test1",
                authorId = 1,
                imageUrl = "https://s3.amazonaws.com/uifaces/faces/twitter/tgerken/128.jpg",
                date = Date()
            )
        )
        Mockito.`when`(
            authorDetailsRepository.getAuthorPosts(
                authorId = 1,
                page = 1,
                limit = Constants.PAGE_SIZE
            )
        )
            .thenReturn(Single.just(comments))
        val response = authorDetailsRepository.getAuthorPosts(
            authorId = 1,
            page = 1,
            limit = Constants.PAGE_SIZE
        )
        for (i in 0 until response.blockingGet().size) {
            response.test()
                .assertValue { it[i].body == "test" + (i + 1) }
        }
    }
}