package com.siddharth.practiceapp.util

import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar


fun Fragment.snackBar(message: String) {
    Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
}