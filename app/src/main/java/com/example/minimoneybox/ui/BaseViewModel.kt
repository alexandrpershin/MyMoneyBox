package com.example.minimoneybox.ui

import android.os.Bundle
import android.os.Parcelable
import androidx.annotation.UiThread
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.example.minimoneybox.api.ErrorType
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

    fun goToWithArgs(destinationId: Int, args: Bundle) {
        _navigation.value = NavigationCommand.WithArgs(destinationId, args)
    }

    @UiThread
    fun goFirstScreen(startDestination: Int) {
        _navigation.value = NavigationCommand.FirstOpen(startDestination)
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

    @UiThread
    fun showSoftKeyboard() {
        _forceKeyboardState.value = KeyboardState.Show
    }

    @UiThread
    fun hideSoftKeyboard() {
        _forceKeyboardState.value = KeyboardState.Hide
    }

}

sealed class KeyboardState {
    object Hide : KeyboardState()
    object Show : KeyboardState()
}
