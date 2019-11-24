package com.symphony.microblogging.data.remote.network.exception

import com.symphony.microblogging.base.domain.exception.MicroBloggingException
import retrofit2.Response
import java.io.IOException

object NetworkException {
    fun httpError(response: Response<Any>?): MicroBloggingException {
        val message: String? = null
        var responseBody = ""
        var statusCode = 0
        val errorCode = 0
        response?.let { statusCode = it.code() }
        response?.let {
            responseBody = it.errorBody()!!.string()
            try {
                // in case of handle http API error
            } catch (exception: Exception) {
            }
        }

        var kind = MicroBloggingException.Kind.HTTP
        when (statusCode) {
            500 -> kind = MicroBloggingException.Kind.SERVER_DOWN
            408 -> kind = MicroBloggingException.Kind.TIME_OUT
            401 -> kind = MicroBloggingException.Kind.UNAUTHORIZED
        }

        return MicroBloggingException(kind, message?.let { message }
            ?: run { "" })
            .setErrorCode(errorCode)
            .setStatusCode(statusCode)
            .setData(responseBody)
    }

    fun networkError(exception: IOException): MicroBloggingException {
        return MicroBloggingException(MicroBloggingException.Kind.NETWORK, exception)
    }

    fun unexpectedError(exception: Throwable): MicroBloggingException {
        return MicroBloggingException(MicroBloggingException.Kind.UNEXPECTED, exception)
    }
}