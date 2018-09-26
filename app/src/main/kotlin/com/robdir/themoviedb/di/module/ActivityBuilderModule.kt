package com.robdir.themoviedb.di.module

import com.robdir.themoviedb.presentation.movielists.popularmovies.PopularMoviesActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector
    abstract fun bindPopularMoviesActivity(): PopularMoviesActivity
}
