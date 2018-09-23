package com.robdir.themoviedb.data.movies

import com.robdir.themoviedb.data.MovieApi
import io.reactivex.Single
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val movieApi: MovieApi
) : MoviesRepositoryContract {

    override fun getPopularMovies(pageNumber: Int): Single<List<MovieEntity>> =
        movieApi.getPopularMovies().map { it.results }
}
