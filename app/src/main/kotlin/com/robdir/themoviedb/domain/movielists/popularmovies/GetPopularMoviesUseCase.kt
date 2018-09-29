package com.robdir.themoviedb.domain.movielists.popularmovies

import com.robdir.themoviedb.data.genres.GenresRepositoryContract
import com.robdir.themoviedb.data.movies.MoviesRepositoryContract
import com.robdir.themoviedb.domain.GenreNameMapper
import com.robdir.themoviedb.domain.movielists.common.Movie
import com.robdir.themoviedb.domain.movielists.common.MovieMapper
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(
    private val moviesRepositoryContract: MoviesRepositoryContract,
    private val genresRepositoryContract: GenresRepositoryContract,
    private val movieMapper: MovieMapper,
    private val genreNameMapper: GenreNameMapper
) : GetPopularMoviesContract {

    override fun getPopularMovies(forceUpdate: Boolean): Single<List<Movie>> =
        moviesRepositoryContract.getPopularMovies(forceUpdate)
            .zipWith(genresRepositoryContract.getGenres(forceUpdate), BiFunction { popularMovies, genres ->
                movieMapper.toDomainModel(popularMovies, genreNameMapper.toNameMap(genres))
            })
}
