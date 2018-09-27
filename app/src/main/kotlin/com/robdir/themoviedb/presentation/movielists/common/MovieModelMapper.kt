package com.robdir.themoviedb.presentation.movielists.common

import android.support.annotation.VisibleForTesting
import com.robdir.themoviedb.di.PosterUrlQualifier
import com.robdir.themoviedb.domain.movielists.common.Movie
import javax.inject.Inject
import kotlin.math.roundToInt

@VisibleForTesting
const val POSTER_WIDTH = 500
@VisibleForTesting
const val THUMBNAIL_WIDTH = 200

private const val DATE_DELIMITER = "-"

class MovieModelMapper @Inject constructor(@PosterUrlQualifier private val posterBaseUrl: String) {
    fun toPresentationModel(movie: Movie): MovieModel =
        with(movie) {
            MovieModel(
                id = id,
                title = title,
                popularity = popularity.roundToInt(),
                thumbnailUrl = "$posterBaseUrl$THUMBNAIL_WIDTH$posterPath",
                posterUrl = "$posterBaseUrl$POSTER_WIDTH/$posterPath",
                releaseYear = releaseDate.substringBefore(DATE_DELIMITER),
                genres = genreNames
            )
        }

    fun toPresentationModel(movieList: List<Movie>): List<MovieModel> =
        movieList.map { toPresentationModel(it) }
}
