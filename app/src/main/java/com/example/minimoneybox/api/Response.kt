package com.example.minimoneybox.api

sealed class TaskResult<out T> {
    data class ErrorResult(val type: ErrorType) : TaskResult<Nothing>()
    data class SuccessResult<T>(val data: T) : TaskResult<T>()
}

inline fun <T> TaskResult<T>.doOnSuccess(callback: (T) -> Unit) {
    if (this is TaskResult.SuccessResult) {
        callback(data)
    }
}

inline fun <T> TaskResult<T>.doOnError(callback: (ErrorType) -> Unit) {
    if (this is TaskResult.ErrorResult) {
        callback(type)
    }
}

sealed class ErrorType {
    data class LoginError(val message: String) : ErrorType()
    data class GenericError(val exception: Exception) : ErrorType()
    data class InternetError(val message: String) : ErrorType()
    data class InputError(val message: String) : ErrorType()
}
