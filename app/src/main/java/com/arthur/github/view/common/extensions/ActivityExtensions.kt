package com.arthur.github.view.common.extensions

import android.app.Activity
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

fun <T : ViewDataBinding?> Activity.createBinding(@LayoutRes resId: Int): T =
        DataBindingUtil.setContentView<T>(this, resId)