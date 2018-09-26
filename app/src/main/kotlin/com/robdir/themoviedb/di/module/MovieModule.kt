package com.robdir.themoviedb.di.module

import com.robdir.themoviedb.data.genres.GenresRepository
import com.robdir.themoviedb.data.genres.GenresRepositoryContract
import com.robdir.themoviedb.data.movies.MoviesRepository
import com.robdir.themoviedb.data.movies.MoviesRepositoryContract
import com.robdir.themoviedb.domain.movielists.popularmovies.GetPopularMoviesContract
import com.robdir.themoviedb.domain.movielists.popularmovies.GetPopularMoviesUseCase
import dagger.Binds
import dagger.Module

@Module
abstract class MovieModule {
    @Binds
    abstract fun provideMoviesRepositoryContract(moviesRepository: MoviesRepository): MoviesRepositoryContract

    @Binds
    abstract fun provideGenresRepositoryContract(genresRepository: GenresRepository): GenresRepositoryContract

    @Binds
    abstract fun provideGetPopularMoviesContract(getPopularMoviesUseCase: GetPopularMoviesUseCase): GetPopularMoviesContract
}
