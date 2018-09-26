package com.robdir.themoviedb.data.movies

import io.reactivex.Single

interface MoviesRepositoryContract {

    fun getPopularMovies(pageNumber: Int): Single<List<MovieEntity>>

    fun getMovieDetails(movieId: Int): Single<MovieDetailEntity>
}
