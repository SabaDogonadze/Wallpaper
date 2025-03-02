package com.example.wallpaperapp.extensions

import android.widget.TextView
import com.google.android.material.snackbar.Snackbar

fun Snackbar.customize(backgroundColor: Int, textColor: Int): Snackbar {
    view.setBackgroundColor(backgroundColor)
    view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)?.setTextColor(textColor)
    return this
}