package com.robdir.themoviedb.domain.movielists.popularmovies

import com.robdir.themoviedb.domain.movielists.common.Movie
import io.reactivex.Single

interface GetPopularMoviesContract {
    fun getPopularMovies(page: Int = 1): Single<List<Movie>>
}
