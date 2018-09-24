package com.robdir.themoviedb.presentation.base

import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v7.app.AppCompatDelegate
import dagger.android.support.DaggerAppCompatActivity

abstract class BaseActivity : DaggerAppCompatActivity() {

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }
}
