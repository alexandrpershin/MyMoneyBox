package com.example.minimoneybox.api


import android.util.Log
import com.example.minimoneybox.R
import com.example.minimoneybox.api.response.CommonServerErrorResponse
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
            TaskResult.ErrorResult(ErrorType.InternetError(R.string.message_internet_error))
        } else {
            TaskResult.ErrorResult(ErrorType.GenericError(message = ex.localizedMessage))
        }
    }
}

private fun <T> parseBackendError(result: Response<T>): TaskResult<T> {
    val errorResponse = getErrorResponse(result.errorBody())
    Log.i(TAG, "Backend error: $errorResponse")

    return when (result.code()) {
        CODE_BAD_REQUEST -> {
            handleBadRequestError(errorResponse)
        }

        CODE_UNAUTHORIZED -> {
            handleUnauthorizedError(errorResponse)
        }

        CODE_INTERNAL_SERVER_ERROR -> {
            TaskResult.ErrorResult(ErrorType.GenericError(resId = R.string.error_message_internal_server_error))
        }
        else -> TaskResult.ErrorResult(ErrorType.GenericError(resId = R.string.error_message_unexpected_server_error))
    }
}

private fun <T> handleUnauthorizedError(errorResponse: CommonServerErrorResponse?): TaskResult<T> {
    return if (errorResponse != null) {
        when (errorResponse.name) {
            KEY_TOKEN_EXPIRED -> { // Can be improved with dedicated error code from backend about token experation
                TaskResult.ErrorResult(ErrorType.TokenExpired(resId = R.string.error_message_session_expired))
            }
            else -> TaskResult.ErrorResult(ErrorType.LoginError(message = errorResponse.message))
        }
    } else {
        TaskResult.ErrorResult(ErrorType.LoginError(resId = R.string.error_message_wrong_credentials))
    }
}

private fun <T> handleBadRequestError(errorResponse: CommonServerErrorResponse?): TaskResult<T> {
    return if (errorResponse != null) {
        TaskResult.ErrorResult(ErrorType.GenericError(message = errorResponse.name))
    } else {
        TaskResult.ErrorResult(ErrorType.GenericError(resId = R.string.error_message_bad_request))
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
private const val KEY_TOKEN_EXPIRED = "Bearer token expired"

private const val CODE_BAD_REQUEST = 400
private const val CODE_UNAUTHORIZED = 401
private const val CODE_INTERNAL_SERVER_ERROR = 500
