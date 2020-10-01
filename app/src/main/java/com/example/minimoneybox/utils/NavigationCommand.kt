package com.example.minimoneybox.utils

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions

sealed class NavigationCommand {
    data class To(val directions: NavDirections) : NavigationCommand()
    data class FirstOpen(val startDestination: Int) : NavigationCommand()
    object Back : NavigationCommand()
    data class BackTo(val destinationId: Int) : NavigationCommand()
    data class BackWithResult(val resultKey: String, val result: Parcelable) : NavigationCommand()
    data class GoToHelp(val arguments: Bundle? = null) : NavigationCommand()
    object ToRoot : NavigationCommand()
    data class WithArgs(
        val destinationId: Int,
        val args: Bundle
    ) : NavigationCommand()
}