package com.symphony.microblogging.base.domain.interactor

import io.reactivex.Single

abstract class SingleUseCaseNoParam<Type> where Type : Any {

    abstract fun build(): Single<Type>
}