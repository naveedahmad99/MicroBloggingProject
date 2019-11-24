package com.symphony.microblogging.base.domain.interactor

import io.reactivex.Single

abstract class SingleUseCase<in Params, Type> where Type : Any {

    abstract fun build(params: Params): Single<Type>
}