package com.robdir.themoviedb

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import com.robdir.themoviedb.di.DaggerAppComponent

class App : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<App> =
        DaggerAppComponent.builder().create(this)
}
