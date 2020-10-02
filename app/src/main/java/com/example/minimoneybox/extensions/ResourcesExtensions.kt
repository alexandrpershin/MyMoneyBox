package com.example.minimoneybox.extensions

import androidx.annotation.*
import androidx.core.content.ContextCompat
import com.example.minimoneybox.app.MoneyboxApplication


fun @receiver:ColorRes Int.getColor(): Int {
    return ContextCompat.getColor(MoneyboxApplication.instance, this)
}

//fun @receiver:StringRes Int.getString(): String {
//    return MoneyboxApplication.instance.getString(this)
//}

//fun @receiver:StringRes Int.getString(vararg formatArgs: Any): String {
//    return MoneyboxApplication.instance.getString(this, *formatArgs)
//}
