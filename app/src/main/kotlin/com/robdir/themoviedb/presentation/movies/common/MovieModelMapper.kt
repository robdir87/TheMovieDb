package com.robdir.themoviedb.presentation.movies.common

import android.support.annotation.VisibleForTesting
import com.robdir.themoviedb.di.PosterUrlQualifier
import com.robdir.themoviedb.domain.movies.Movie
import javax.inject.Inject
import kotlin.math.roundToInt

@VisibleForTesting
const val POSTER_WIDTH = 500
@VisibleForTesting
const val THUMBNAIL_WIDTH = 110

private const val GENRE_SEPARATOR = ", "
private const val DATE_DELIMITER = "-"

class MovieModelMapper @Inject constructor(@PosterUrlQualifier private val posterBaseUrl: String) {
    fun toPresentationModel(movie: Movie): MovieModel =
        with(movie) {
            MovieModel(
                id = id,
                title = title,
                popularity = popularity.roundToInt(),
                thumbnailUrl = "$posterBaseUrl$THUMBNAIL_WIDTH/$posterPath",
                posterUrl = "$posterBaseUrl$POSTER_WIDTH/$posterPath",
                releaseYear = releaseDate.substringBefore(DATE_DELIMITER),
                genres = genreNames.joinToString(separator = GENRE_SEPARATOR)
            )
        }

    fun toPresentationModel(movieList: List<Movie>): List<MovieModel> =
        movieList.map { toPresentationModel(it) }
}
