package com.robdir.themoviedb.presentation.common

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

@JvmOverloads
fun ViewGroup.inflate(@LayoutRes layoutResource: Int, attachToRoot: Boolean = false): View =
    LayoutInflater.from(context).inflate(layoutResource, this, attachToRoot)

fun View.visibleIf(predicate: Boolean, otherwiseVisibility: Int = View.GONE) {
    visibility = if (predicate) View.VISIBLE else otherwiseVisibility
}

fun View.gone() {
    visibility = View.GONE
}

fun View.visible() {
    visibility = View.VISIBLE
}
