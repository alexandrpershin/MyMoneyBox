package com.example.minimoneybox.ui

import android.os.Bundle
import androidx.annotation.StringRes
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

    private val _messageNotifier: SingleLiveEvent<MessageType> = SingleLiveEvent()
    val messageNotifier
        get() = _messageNotifier

    @UiThread
    fun goTo(direction: NavDirections) {
        _navigation.value = NavigationCommand.To(direction)
    }

    fun goToWithArgs(destinationId: Int, args: Bundle) {
        _navigation.value = NavigationCommand.WithArgs(destinationId, args)
    }


    fun resetGraph(newGraphId: Int) {
        _navigation.value = NavigationCommand.ResetGraph(newGraphId)
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
    fun showMessage(messageType: MessageType) {
        _messageNotifier.value = messageType
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

sealed class MessageType(
    @StringRes val resId: Int,
    val message: String? = null
) {
    class Success(resId: Int, message: String? = null) : MessageType(resId, message)
    class Error(resId: Int, message: String? = null) : MessageType(resId, message)
    class Info(resId: Int, message: String? = null) : MessageType(resId, message)
}
