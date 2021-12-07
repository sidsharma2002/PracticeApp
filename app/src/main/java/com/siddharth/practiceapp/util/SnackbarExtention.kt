package com.siddharth.practiceapp.util

import android.app.Activity
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar


fun Fragment.snackBar(message: String) {
    Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
}

fun Activity.snackBar(message: String) {
    Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show()
}