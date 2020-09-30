package com.example.minimoneybox.api


import android.util.Log
import com.example.minimoneybox.R
import com.example.minimoneybox.api.response.CommonServerErrorResponse
import com.example.minimoneybox.extensions.getString
import com.google.gson.Gson
import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.Response
import java.net.UnknownHostException


suspend fun <T> executeAsyncRequest(request: Deferred<Response<T>>): TaskResult<T> {

    return try {
        val result = request.await()

        if (result.isSuccessful) {
            Log.d(TAG, "Result is successful")
            TaskResult.SuccessResult(data = result.body() as T)
        } else {
            Log.d(TAG, "Result is not successful, code: ${result.code()} ")
            parseBackendError(result)
        }
    } catch (ex: Exception) {
        Log.d(TAG, "Exception message: ${ex.message}")
        if (ex is UnknownHostException) {
            TaskResult.ErrorResult(ErrorType.InternetError(R.string.message_internet_error.getString()))
        } else {
            TaskResult.ErrorResult(ErrorType.GenericError(exception = ex))
        }
    }
}

private fun <T> parseBackendError(result: Response<T>): TaskResult<T> {
    val errorResponse = getErrorResponse(result.errorBody())
    Log.i(TAG, "Backend error: $errorResponse")

    return when (result.code()) {
        CODE_BAD_REQUEST -> {
            TaskResult.ErrorResult(ErrorType.GenericError(Exception(R.string.error_message_bad_request.getString())))
        }

        CODE_UNAUTHORIZED -> {
            handleUnauthorizedError(errorResponse)
        }

        CODE_INTERNAL_SERVER_ERROR -> {
            TaskResult.ErrorResult(ErrorType.GenericError(Exception(R.string.error_message_internal_server_error.getString())))
        }
        else -> TaskResult.ErrorResult(ErrorType.GenericError(Exception(R.string.error_message_unexpected_server_error.getString())))
    }
}

private fun <T> handleUnauthorizedError(errorResponse: CommonServerErrorResponse?): TaskResult<T> {
    return if (errorResponse != null) {
        TaskResult.ErrorResult(ErrorType.LoginError(message = errorResponse.message))
    } else {
        TaskResult.ErrorResult(ErrorType.LoginError(R.string.error_message_wrong_credentials.getString()))
    }
}

private fun getErrorResponse(errorResponseBody: ResponseBody?): CommonServerErrorResponse? {
    return if (errorResponseBody != null) {
        val gson = Gson()
        val errorString = errorResponseBody.string()
        val errorResponse = gson.fromJson<CommonServerErrorResponse>(
            errorString,
            CommonServerErrorResponse::class.java
        )
        errorResponse
    } else {
        null
    }
}

private const val TAG = "ApiExecutor"

private const val CODE_BAD_REQUEST = 400
private const val CODE_UNAUTHORIZED = 401
private const val CODE_INTERNAL_SERVER_ERROR = 500
