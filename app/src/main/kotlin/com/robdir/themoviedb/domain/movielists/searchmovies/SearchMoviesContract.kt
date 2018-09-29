package com.robdir.themoviedb.domain.movielists.searchmovies

import com.robdir.themoviedb.domain.movielists.common.Movie
import io.reactivex.Single

interface SearchMoviesContract {

    fun searchMovies(query: String): Single<List<Movie>>
}
