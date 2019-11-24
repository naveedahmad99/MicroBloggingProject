package com.symphony.microblogging.base.presentation.model

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.symphony.microblogging.base.domain.exception.MicroBloggingException

class ObservableResource<T> : SingleLiveEvent<T>() {

    val error: SingleLiveEvent<MicroBloggingException> = ErrorLiveData()
    val loading: SingleLiveEvent<Boolean> = SingleLiveEvent()

    fun observe(
        owner: LifecycleOwner, successObserver: Observer<T>,
        loadingObserver: Observer<Boolean>? = null,
        commonErrorObserver: Observer<MicroBloggingException>,
        httpErrorConsumer: Observer<MicroBloggingException>? = null,
        networkErrorConsumer: Observer<MicroBloggingException>? = null,
        unExpectedErrorConsumer: Observer<MicroBloggingException>? = null,
        serverDownErrorConsumer: Observer<MicroBloggingException>? = null,
        timeOutErrorConsumer: Observer<MicroBloggingException>? = null,
        unAuthorizedErrorConsumer: Observer<MicroBloggingException>? = null
    ) {
        super.observe(owner, successObserver)
        loadingObserver?.let { loading.observe(owner, it) }
        (error as ErrorLiveData).observe(
            owner, commonErrorObserver, httpErrorConsumer, networkErrorConsumer, unExpectedErrorConsumer,
            serverDownErrorConsumer, timeOutErrorConsumer, unAuthorizedErrorConsumer
        )
    }


    class ErrorLiveData : SingleLiveEvent<MicroBloggingException>() {
        private var ownerRef: LifecycleOwner? = null
        private var httpErrorConsumer: Observer<MicroBloggingException>? = null
        private var networkErrorConsumer: Observer<MicroBloggingException>? = null
        private var unExpectedErrorConsumer: Observer<MicroBloggingException>? = null
        private var commonErrorConsumer: Observer<MicroBloggingException>? = null

        private var serverDownErrorConsumer: Observer<MicroBloggingException>? = null
        private var timeOutErrorConsumer: Observer<MicroBloggingException>? = null
        private var unAuthorizedErrorConsumer: Observer<MicroBloggingException>? = null

        override fun setValue(t: MicroBloggingException?) {
            ownerRef?.also {
                removeObservers(it)
                t?.let { vale -> addProperObserver(vale) }
                super.setValue(t)
            }

        }

        override fun postValue(value: MicroBloggingException) {
            ownerRef?.also {
                removeObservers(it)
                addProperObserver(value)
                super.postValue(value)
            }

        }

        private fun addProperObserver(value: MicroBloggingException) {
            when (value.kind) {
                MicroBloggingException.Kind.NETWORK -> networkErrorConsumer?.let { observe(ownerRef!!, it) }
                    ?: observe(ownerRef!!, commonErrorConsumer!!)
                MicroBloggingException.Kind.HTTP -> httpErrorConsumer?.let { observe(ownerRef!!, it) }
                    ?: observe(ownerRef!!, commonErrorConsumer!!)
                MicroBloggingException.Kind.UNEXPECTED -> unExpectedErrorConsumer?.let { observe(ownerRef!!, it) }
                    ?: observe(ownerRef!!, commonErrorConsumer!!)

                MicroBloggingException.Kind.SERVER_DOWN -> serverDownErrorConsumer?.let { observe(ownerRef!!, it) }
                    ?: observe(ownerRef!!, commonErrorConsumer!!)

                MicroBloggingException.Kind.TIME_OUT -> timeOutErrorConsumer?.let { observe(ownerRef!!, it) }
                    ?: observe(ownerRef!!, commonErrorConsumer!!)

                MicroBloggingException.Kind.UNAUTHORIZED -> unAuthorizedErrorConsumer?.let { observe(ownerRef!!, it) }
                    ?: observe(ownerRef!!, commonErrorConsumer!!)

                else -> {
                }
            }
        }


        fun observe(
            owner: LifecycleOwner, commonErrorConsumer: Observer<MicroBloggingException>,
            httpErrorConsumer: Observer<MicroBloggingException>? = null,
            networkErrorConsumer: Observer<MicroBloggingException>? = null,
            unExpectedErrorConsumer: Observer<MicroBloggingException>? = null,

            serverDownErrorConsumer: Observer<MicroBloggingException>? = null,
            timeOutErrorConsumer: Observer<MicroBloggingException>? = null,
            unAuthorizedErrorConsumer: Observer<MicroBloggingException>? = null
        ) {
            super.observe(owner, commonErrorConsumer)
            this.ownerRef = owner
            this.commonErrorConsumer = commonErrorConsumer
            this.httpErrorConsumer = httpErrorConsumer
            this.networkErrorConsumer = networkErrorConsumer
            this.unExpectedErrorConsumer = unExpectedErrorConsumer
            this.serverDownErrorConsumer = serverDownErrorConsumer
            this.timeOutErrorConsumer = timeOutErrorConsumer
            this.unAuthorizedErrorConsumer = unAuthorizedErrorConsumer
        }
    }
}