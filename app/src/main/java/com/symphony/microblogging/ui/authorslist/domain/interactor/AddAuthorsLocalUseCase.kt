package com.symphony.microblogging.ui.authorslist.domain.interactor

import com.symphony.microblogging.data.local.entity.AuthorEntity
import com.symphony.microblogging.base.domain.interactor.SingleUseCase
import com.symphony.microblogging.ui.authorslist.domain.repository.AuthorsRepository
import io.reactivex.Single
import javax.inject.Inject

class AddAuthorsLocalUseCase @Inject constructor(private val repository: AuthorsRepository) :
    SingleUseCase<List<AuthorEntity>, Boolean>() {
    override fun build(params: List<AuthorEntity>): Single<Boolean> =
        repository.insertAuthors(authors = params)
}