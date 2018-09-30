package com.robdir.themoviedb.di.module

import com.robdir.themoviedb.data.genres.GenresRepository
import com.robdir.themoviedb.data.genres.GenresRepositoryContract
import com.robdir.themoviedb.data.movies.MoviesRepository
import com.robdir.themoviedb.data.movies.MoviesRepositoryContract
import com.robdir.themoviedb.domain.moviedetails.GetMovieDetailsContract
import com.robdir.themoviedb.domain.moviedetails.GetMovieDetailsUseCase
import com.robdir.themoviedb.domain.movielists.popularmovies.GetPopularMoviesContract
import com.robdir.themoviedb.domain.movielists.popularmovies.GetPopularMoviesUseCase
import com.robdir.themoviedb.domain.movielists.searchmovies.SearchMoviesContract
import com.robdir.themoviedb.domain.movielists.searchmovies.SearchMoviesUseCase
import dagger.Binds
import dagger.Module

@Module
abstract class MoviesModule {
    @Binds
    abstract fun bindMoviesRepositoryContract(moviesRepository: MoviesRepository): MoviesRepositoryContract

    @Binds
    abstract fun bindGenresRepositoryContract(genresRepository: GenresRepository): GenresRepositoryContract

    @Binds
    abstract fun bindGetPopularMoviesContract(getPopularMoviesUseCase: GetPopularMoviesUseCase): GetPopularMoviesContract

    @Binds
    abstract fun bindGetMovieDetailsContract(getMovieDetailsUseCase: GetMovieDetailsUseCase): GetMovieDetailsContract

    @Binds
    abstract fun bindSearchMoviesContract(searchMoviesUseCase: SearchMoviesUseCase): SearchMoviesContract
}
