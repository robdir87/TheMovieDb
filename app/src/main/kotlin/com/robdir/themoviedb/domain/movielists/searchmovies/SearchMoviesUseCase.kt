package com.robdir.themoviedb.domain.movielists.searchmovies

import com.robdir.themoviedb.data.genres.GenresRepositoryContract
import com.robdir.themoviedb.data.movies.MoviesRepositoryContract
import com.robdir.themoviedb.domain.GenreNameMapper
import com.robdir.themoviedb.domain.movielists.common.Movie
import com.robdir.themoviedb.domain.movielists.common.MovieMapper
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class SearchMoviesUseCase @Inject constructor(
    private val moviesRepositoryContract: MoviesRepositoryContract,
    private val genresRepositoryContract: GenresRepositoryContract,
    private val movieMapper: MovieMapper,
    private val genreNameMapper: GenreNameMapper
) : SearchMoviesContract {

    override fun searchMovies(query: String): Single<List<Movie>> =
        moviesRepositoryContract.searchMovies(query)
            .flatMap { movies -> Single.just(movies.sortedByDescending { it.popularity }) }
            .zipWith(genresRepositoryContract.getGenres(forceUpdate = true), BiFunction { popularMovies, genres ->
                movieMapper.toDomainModel(popularMovies, genreNameMapper.toNameMap(genres))
            })
}
