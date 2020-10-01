package com.example.minimoneybox.api

import com.example.minimoneybox.R
import com.example.minimoneybox.extensions.getString

sealed class TaskResult<out T> {
    data class ErrorResult(val errorType: ErrorType) : TaskResult<Nothing>()
    data class SuccessResult<T>(val data: T) : TaskResult<T>()
}

inline fun <T> TaskResult<T>.doOnSuccess(callback: (T) -> Unit) {
    if (this is TaskResult.SuccessResult) {
        callback(data)
    }
}

inline fun <T> TaskResult<T>.doOnError(callback: (ErrorType) -> Unit) {
    if (this is TaskResult.ErrorResult) {
        callback(errorType)
    }
}

sealed class ErrorType {
    data class LoginError(val message: String) : ErrorType()
    data class TokenExpired(val message: String) : ErrorType()
    data class GenericError(
        val exception: Exception,
        val message: String = exception.localizedMessage
            ?: R.string.error_message_unexpected_error.getString()
    ) : ErrorType()

    data class InternetError(val message: String) : ErrorType()
    data class InputError(val message: String) : ErrorType()
}
