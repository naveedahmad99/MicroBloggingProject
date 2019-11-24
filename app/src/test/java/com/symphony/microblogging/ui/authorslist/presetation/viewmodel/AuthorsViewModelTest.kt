package com.symphony.microblogging.ui.authorslist.presetation.viewmodel

import com.symphony.microblogging.data.remote.network.response.Address
import com.symphony.microblogging.data.remote.network.response.Author
import com.symphony.microblogging.ui.authorslist.domain.interactor.AddAuthorsLocalUseCase
import com.symphony.microblogging.ui.authorslist.domain.interactor.GetAuthorsUseCase
import com.symphony.microblogging.ui.authorslist.domain.interactor.GetCachedAuthorsUseCase
import com.symphony.microblogging.ui.authorslist.domain.repository.AuthorsRepository
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class AuthorsViewModelTest {

    @Mock
    private lateinit var authorsRepository: AuthorsRepository

    @Mock
    private lateinit var getAuthorsUseCase: GetAuthorsUseCase

    @Mock
    private lateinit var addAuthorsLocalUseCase: AddAuthorsLocalUseCase

    @Mock
    private lateinit var getCachedAuthorsUseCase: GetCachedAuthorsUseCase

    private lateinit var authorsViewModel: AuthorsViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        authorsViewModel =
            AuthorsViewModel(getAuthorsUseCase, addAuthorsLocalUseCase, getCachedAuthorsUseCase)
    }

    @Test
    fun checkResponseOfAuthorsNotEmpty() {
        val authors = listOf(
            Author(
                name = "test1",
                authorID = 1,
                avatarUrl = "https://s3.amazonaws.com/uifaces/faces/twitter/tgerken/128.jpg",
                userName = "test1",
                email = "test1@gmail.com",
                address = Address(10.0, 10.0)
            )
        )
        Mockito.`when`(authorsRepository.getAuthors())
            .thenReturn(Single.just(authors))
        val response = authorsRepository.getAuthors()
        response.test()
            .assertValue { it.isNotEmpty() }
    }

    @Test
    fun checkResponseOfAuthorsName() {
        val authors = listOf(
            Author(
                name = "test1",
                authorID = 1,
                avatarUrl = "https://s3.amazonaws.com/uifaces/faces/twitter/tgerken/128.jpg",
                userName = "test1",
                email = "test1@gmail.com",
                address = Address(10.0, 10.0)
            )
        )
        Mockito.`when`(authorsRepository.getAuthors())
            .thenReturn(Single.just(authors))
        val response = authorsRepository.getAuthors()
        for (i in 0 until response.blockingGet().size) {
            response.test()
                .assertValue { it[i].name == "test" + (i + 1) }
        }
    }
}