package com.robdir.themoviedb.di.module

import com.robdir.themoviedb.presentation.moviedetails.MovieDetailsActivity
import com.robdir.themoviedb.presentation.movielists.popularmovies.PopularMoviesActivity
import com.robdir.themoviedb.presentation.movielists.searchmovies.SearchMoviesActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector
    abstract fun bindPopularMoviesActivity(): PopularMoviesActivity

    @ContributesAndroidInjector
    abstract fun bindMovieDetailsActivity(): MovieDetailsActivity

    @ContributesAndroidInjector
    abstract fun bindSearchMoviesActivity(): SearchMoviesActivity
}
