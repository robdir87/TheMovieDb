package com.robdir.themoviedb.domain.popularmovies

import android.support.annotation.VisibleForTesting
import com.robdir.themoviedb.data.genres.GenreEntity
import com.robdir.themoviedb.data.genres.GenresRepositoryContract
import com.robdir.themoviedb.data.movies.MoviesRepositoryContract
import com.robdir.themoviedb.domain.GenreNameMapper
import com.robdir.themoviedb.domain.movies.Movie
import com.robdir.themoviedb.domain.movies.MovieMapper
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(
    private val moviesRepositoryContract: MoviesRepositoryContract,
    private val genresRepositoryContract: GenresRepositoryContract,
    private val movieMapper: MovieMapper,
    private val genreNameMapper: GenreNameMapper
) : GetPopularMoviesContract {

    override fun getPopularMovies(page: Int): Single<List<Movie>> =
        moviesRepositoryContract.getPopularMovies(page)
            .zipWith(genresRepositoryContract.getGenres(), BiFunction { popularMovies, genres ->
                movieMapper.toDomainModel(popularMovies, genres.toMap())
            })

    @VisibleForTesting
    fun List<GenreEntity>.toMap(): Map<Int, String> = map { it.id to it.name }.toMap()

}
