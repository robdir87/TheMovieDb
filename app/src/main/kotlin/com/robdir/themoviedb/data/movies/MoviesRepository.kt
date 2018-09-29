package com.robdir.themoviedb.data.movies

import android.support.annotation.VisibleForTesting
import com.robdir.themoviedb.data.MovieApi
import com.robdir.themoviedb.data.persistence.PopularMoviesDao
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val movieApi: MovieApi,
    private val popularMoviesDao: PopularMoviesDao
) : MoviesRepositoryContract {

    override fun getPopularMovies(forceUpdate: Boolean): Single<List<MovieEntity>> =
        if (forceUpdate) {
            deletePopularMoviesFromDatabase().andThen(getPopularMoviesFromApi())
        } else {
            popularMoviesDao.getPopularMovies()
                .flatMap { movies ->
                    if (movies.isEmpty()) getPopularMoviesFromApi() else Single.just(movies)
                }.onErrorResumeNext { getPopularMoviesFromApi() }
        }

    override fun getMovieDetails(movieId: Int): Single<MovieDetailEntity> =
        movieApi.getMovieDetails(movieId = movieId)

    override fun searchMovies(query: String): Single<List<MovieEntity>> =
        movieApi.searchMovies(query = query).map { it.results }

    // region Private methods
    @VisibleForTesting
    fun getPopularMoviesFromApi(): Single<List<MovieEntity>> =
        movieApi.getPopularMovies().map { it.results }.doOnSuccess(popularMoviesDao::savePopularMovies)

    private fun deletePopularMoviesFromDatabase(): Completable = Completable.create { emitter ->
        popularMoviesDao.deleteMovies()
        emitter.onComplete()
    }
    // endregion
}
