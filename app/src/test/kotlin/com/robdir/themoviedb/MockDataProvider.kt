package com.robdir.themoviedb

import com.robdir.themoviedb.data.genres.GenreEntity
import com.robdir.themoviedb.data.movies.MovieEntity
import com.robdir.themoviedb.domain.movies.Movie
import com.robdir.themoviedb.presentation.movies.common.MovieModel
import com.robdir.themoviedb.presentation.movies.common.POSTER_WIDTH
import com.robdir.themoviedb.presentation.movies.common.THUMBNAIL_WIDTH

object MockDataProvider {
    // Genre
    const val mockGenreId = 0
    const val mockGenreName = "Horror"
    const val mockAnotherGenreId = 1
    const val mockAnotherGenreName = "Fantasy"

    // Movie
    const val mockMovieId = 123
    const val mockMovieTitle = "Some horror movie"
    const val mockMoviePopularity = 7.1
    const val mockRoundedPopularity = 7
    const val mockMoviePosterPath = "some-poster-path"
    const val mockMovieReleaseDate = "2018-03-15"
    const val mockMovieReleaseYear = "2018"
    const val mockMoviePosterBaseUrl = "http://some-base-url.com/w"
    const val mockMoviePosterUrl = "http://some-base-url.com/w$POSTER_WIDTH/$mockMoviePosterPath"
    const val mockMovieThumbnailUrl = "http://some-base-url.com/w$THUMBNAIL_WIDTH/$mockMoviePosterPath"

    val mockMovieGenreIds = listOf(mockGenreId)
    val mockMovieGenreNames = listOf(mockGenreName)
    val mockMovieGenreMap = mapOf(mockGenreId to mockGenreName, mockAnotherGenreId to mockAnotherGenreName)

    // region Provider methods
    fun createMockMovieEntity(): MovieEntity =
        MovieEntity(
            mockMovieId, mockMovieTitle, mockMoviePopularity, mockMoviePosterPath, mockMovieReleaseDate, mockMovieGenreIds
        )

    fun createMockMovie(genreNames: List<String> = mockMovieGenreNames): Movie =
        Movie(
            mockMovieId, mockMovieTitle, mockMoviePopularity, mockMoviePosterPath, mockMovieReleaseDate, genreNames
        )

    fun createMockGenreEntity(): GenreEntity = GenreEntity(mockGenreId, mockGenreName)

    fun createMockMovieModel(genres: String = mockGenreName): MovieModel =
        MovieModel(
            mockMovieId, mockMovieTitle, mockRoundedPopularity, mockMovieThumbnailUrl, mockMoviePosterUrl, mockMovieReleaseYear, genres
        )
    // endregion
}
