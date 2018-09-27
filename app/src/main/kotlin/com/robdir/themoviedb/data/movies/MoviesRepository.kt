package com.robdir.themoviedb.data.movies

import com.robdir.themoviedb.data.MovieApi
import com.robdir.themoviedb.data.persistence.PopularMoviesDao
import io.reactivex.Single
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val movieApi: MovieApi,
    private val popularMoviesDao: PopularMoviesDao
) : MoviesRepositoryContract {

    override fun getPopularMovies(pageNumber: Int): Single<List<MovieEntity>> =
        movieApi.getPopularMovies().map { it.results }

    override fun getMovieDetails(movieId: Int): Single<MovieDetailEntity> =
        movieApi.getMovieDetails(movieId = movieId)

    // region Private methods
    private fun getPopularMoviesFromDatabase(): Single<List<MovieEntity>> =
        popularMoviesDao.getPopularMovies()

    private fun savePopularMoviesToDatabase(popularMovies: List<MovieEntity>, clearBefore: Boolean = false) {
        if (clearBefore) popularMoviesDao.deleteMovies()
        popularMoviesDao.savePopularMovies(popularMovies)
    }
    // endregion
}
