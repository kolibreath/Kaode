package com.example.kolibreath.kgaodetest.extensions

import android.app.Activity
import android.support.annotation.IdRes
import android.view.View

fun <T: View> Activity.find(@IdRes res:Int): Lazy<T> {

    @Suppress("UNCHECKED_CAST")
    return lazy {
        findViewById(res) as T
    }
}