package com.robdir.themoviedb.data.movies

import io.reactivex.Single

interface MoviesRepositoryContract {

    fun getPopularMovies(forceUpdate: Boolean): Single<List<MovieEntity>>

    fun getMovieDetails(movieId: Int): Single<MovieDetailEntity>
}
