package com.robdir.themoviedb.domain.popularmovies

import com.robdir.themoviedb.domain.movies.Movie
import io.reactivex.Single

interface GetPopularMoviesContract {
    fun getPopularMovies(page: Int = 1): Single<List<Movie>>
}
