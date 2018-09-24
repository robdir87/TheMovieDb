package com.robdir.themoviedb.presentation.movies.common

import com.robdir.themoviedb.domain.movies.Movie
import javax.inject.Inject
import kotlin.math.roundToInt

private const val POSTER_BASE_URL = "http://image.tmdb.org/t/p/w500"
private const val GENRE_SEPARATOR = ", "
private const val DATE_DELIMITER = "-"

class MovieModelMapper @Inject constructor() {
    fun toPresentationModel(movie: Movie): MovieModel =
        with(movie) {
            MovieModel(
                id = id,
                title = title,
                popularity = popularity.roundToInt(),
                posterUrl = "$POSTER_BASE_URL/$posterPath",
                releaseYear = releaseDate.substringBefore(DATE_DELIMITER),
                genres = genreNames.joinToString(separator = GENRE_SEPARATOR)
            )
        }

    fun toPresentationModel(movieList: List<Movie>): List<MovieModel> =
        movieList.map { toPresentationModel(it) }
}
