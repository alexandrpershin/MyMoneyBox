package com.example.minimoneybox.ui

import android.os.Parcelable
import androidx.annotation.UiThread
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.example.minimoneybox.utils.NavigationCommand
import com.example.minimoneybox.utils.SingleLiveEvent

abstract class BaseViewModel : ViewModel() {

    private val _navigation: SingleLiveEvent<NavigationCommand> = SingleLiveEvent()
    val navigation
        get() = _navigation

    private val _errorNotifier: SingleLiveEvent<ErrorType> = SingleLiveEvent()
    val errorNotifier
        get() = _errorNotifier

    private val _loadingState: SingleLiveEvent<Boolean> = SingleLiveEvent()
    val loadingState
        get() = _loadingState

    private val _forceKeyboardState: SingleLiveEvent<KeyboardState> = SingleLiveEvent()
    val forceKeyboardState
        get() = _forceKeyboardState

    @UiThread
    fun goTo(direction: NavDirections) {
        _navigation.value = NavigationCommand.To(direction)
    }

    @UiThread
    fun goFirstScreen(direction: NavDirections, destinationId: Int) {
        _navigation.value = NavigationCommand.FirstOpen(direction, destinationId)
    }

    @UiThread
    fun goBack() {
        _navigation.value = NavigationCommand.Back
    }

    @UiThread
    fun goBackTo(directionId: Int) {
        _navigation.value = NavigationCommand.BackTo(directionId)
    }

    @UiThread
    fun goBackWithResult(resultKey: String, result: Parcelable) {
        _navigation.value = NavigationCommand.BackWithResult(resultKey, result)
    }

    @UiThread
    fun notifyError(error: ErrorType) {
        _errorNotifier.value = error
    }

    @UiThread
    fun showLoading() {
        _loadingState.value = true
    }

    @UiThread
    fun hideLoading() {
        _loadingState.value = false
    }

}

sealed class ErrorType {
    data class GenericError(val message: String) : ErrorType()
    data class LoginError(val message: String) : ErrorType()
}

sealed class KeyboardState {
    object Hide : KeyboardState()
    object Show : KeyboardState()
}
