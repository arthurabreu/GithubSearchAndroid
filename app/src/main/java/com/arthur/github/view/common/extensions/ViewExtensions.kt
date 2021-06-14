package com.arthur.github.view.common.extensions

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.createSnackBar(msg: String, length: Int = Snackbar.LENGTH_SHORT) =
        Snackbar.make(this, msg, length).show()