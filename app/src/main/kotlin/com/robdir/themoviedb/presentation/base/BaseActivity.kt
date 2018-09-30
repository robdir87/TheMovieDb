package com.robdir.themoviedb.presentation.base

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatDelegate
import android.widget.ImageView
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

abstract class BaseActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }

    protected fun startActivityWithTransitionAnimation(intent: Intent, sharedImageView: ImageView) {
        startActivity(
            intent,
            ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,
                sharedImageView,
                ViewCompat.getTransitionName(sharedImageView)
            ).toBundle()
        )
    }

    protected inline fun <reified VM : BaseViewModel> viewModel(
        noinline onErrorChanged: (() -> Unit)? = null,
        noinline onNetworkErrorChanged: (() -> Unit)? = null,
        noinline onLoadingChanged: ((Boolean?) -> Unit)? = null
    ): VM =
        ViewModelProviders.of(this, viewModelFactory)[VM::class.java]
            .apply {
                onErrorChanged?.run { error.observe(this@BaseActivity, Observer { this() }) }
                onNetworkErrorChanged?.run { networkError.observe(this@BaseActivity, Observer { this() }) }
                onLoadingChanged?.run { isLoading.observe(this@BaseActivity, Observer { value -> this(value) }) }
            }
}
