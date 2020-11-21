package com.example.minimoneybox.utils

import android.os.Bundle
import androidx.navigation.NavDirections

sealed class NavigationCommand {
    data class To(val directions: NavDirections) : NavigationCommand()
    data class ResetGraph(val newGraphId: Int) : NavigationCommand()
    object Back : NavigationCommand()
    data class BackTo(val destinationId: Int) : NavigationCommand()
    data class WithArgs(
        val destinationId: Int,
        val args: Bundle
    ) : NavigationCommand()
}