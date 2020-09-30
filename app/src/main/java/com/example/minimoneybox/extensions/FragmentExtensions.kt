package com.example.minimoneybox.extensions

import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar


fun Fragment.showToast(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}

fun Fragment.showErrorMessage(message: String) {
    showSnackBar(message)
}

fun Fragment.showSnackBar(message: String) {
    val view = activity!!.findViewById<View>(android.R.id.content)
    val snackBar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
    snackBar.show()
}