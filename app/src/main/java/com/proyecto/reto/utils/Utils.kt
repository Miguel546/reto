package com.proyecto.reto.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.databinding.BindingAdapter

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

@BindingAdapter("isShowView")
fun View.setShowView(value: Boolean?){
    value?.let { visible ->
        visibility = if (visible) View.VISIBLE else View.GONE
    }
}