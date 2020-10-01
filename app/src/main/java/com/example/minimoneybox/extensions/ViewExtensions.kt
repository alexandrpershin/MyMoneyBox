package com.example.minimoneybox.extensions

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.EditText
import androidx.annotation.LayoutRes
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.minimoneybox.R

/**
 * View extensions
 * */

fun View.playBounceAnimation(){
    val bounceAnim = AnimationUtils.loadAnimation(context, R.anim.anim_bounce)
    startAnimation(bounceAnim)
}

fun View.makeGone() {
    this.visibility = View.GONE
}

fun View.makeVisible() {
    this.visibility = View.VISIBLE
}

fun View.setVisible(visible: Boolean) {
    visibility = if (visible) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

fun View.isVisible(): Boolean {
    return visibility == View.VISIBLE
}

fun View.makeInVisible() {
    visibility = View.INVISIBLE
}


fun View.disableClick() {
    isFocusable = false
    isClickable = false
}

fun View.enableClick() {
    isFocusable = true
    isClickable = true
}

/**
 * SwipeRefreshLayout extensions
 * */

fun SwipeRefreshLayout.stopRefreshing() {
    isRefreshing = false
}

fun SwipeRefreshLayout.startRefreshing() {
    isRefreshing = true
}

/**
 * MenuItem extensions
 * */

fun MenuItem?.makeGone() {
    this?.isVisible = false
}

/**
 * ViewGroup extensions
 * */

internal fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

/**
 * EditText extensions
 * */

fun EditText.onChange(cb: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            cb(s.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    })
}