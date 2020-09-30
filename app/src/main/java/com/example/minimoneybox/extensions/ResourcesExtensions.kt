package com.example.minimoneybox.extensions

import android.graphics.Bitmap
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import androidx.annotation.*
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import com.example.minimoneybox.app.MoneyboxApplication

fun @receiver:FontRes Int.getTypeface(): Typeface? {
    return ResourcesCompat.getFont(MoneyboxApplication.instance, this)
}

fun @receiver:ColorRes Int.getColor(): Int {
    return ContextCompat.getColor(MoneyboxApplication.instance, this)
}

fun @receiver:StringRes Int.getString(): String {
    return MoneyboxApplication.instance.getString(this)
}

fun @receiver:StringRes Int.getString(vararg formatArgs: Any): String {
    return MoneyboxApplication.instance.getString(this, *formatArgs)
}

fun @receiver:PluralsRes Int.getPlural(quantity: Int, vararg formatArgs: Any): String {
    return MoneyboxApplication.instance.resources.getQuantityString(this, quantity, *formatArgs)
}

fun @receiver:DrawableRes Int.getDrawable(): Drawable {
    return AppCompatResources.getDrawable(MoneyboxApplication.instance, this)!!
}

fun @receiver:DrawableRes Int.getBitmap(): Bitmap {
    return AppCompatResources.getDrawable(MoneyboxApplication.instance, this)!!.toBitmap()
}

fun @receiver:DrawableRes Int.getBitmapTinted(@ColorRes color: Int): Bitmap {
    return AppCompatResources.getDrawable(MoneyboxApplication.instance, this)!!.toBitmap(color)
}

fun @receiver:DimenRes Int.getDimension(): Float {
    return MoneyboxApplication.instance.resources.getDimension(this)
}

fun @receiver:DimenRes Int.getDimensionPixelOffset(): Int {
    return MoneyboxApplication.instance.resources.getDimensionPixelOffset(this)
}

fun @receiver:DimenRes Int.getDimensionPixelSize(): Int {
    return MoneyboxApplication.instance.resources.getDimensionPixelSize(this)
}