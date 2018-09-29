package com.robdir.themoviedb.di.module

import com.robdir.themoviedb.presentation.moviedetails.MovieDetailActivity
import com.robdir.themoviedb.presentation.movielists.popularmovies.PopularMoviesActivity
import com.robdir.themoviedb.presentation.movielists.searchmovies.SearchMoviesActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector
    abstract fun bindPopularMoviesActivity(): PopularMoviesActivity

    @ContributesAndroidInjector
    abstract fun bindMovieDetailActivity(): MovieDetailActivity

    @ContributesAndroidInjector
    abstract fun bindSearchMoviesActivity(): SearchMoviesActivity
}
