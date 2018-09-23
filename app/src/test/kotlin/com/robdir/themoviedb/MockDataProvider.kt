package com.robdir.themoviedb

import com.robdir.themoviedb.data.genres.GenreEntity
import com.robdir.themoviedb.data.movies.MovieEntity
import com.robdir.themoviedb.domain.movies.Movie

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
    const val mockMoviePosterPath = "some-poster-path"
    const val mockMovieReleaseDate = "2018-03-15"

    val mockMovieGenreIds = listOf(mockGenreId)
    val mockMovieGenreNames = listOf(mockGenreName)
    val mockMovieGenreMap = mapOf(mockGenreId to mockGenreName, mockAnotherGenreId to mockAnotherGenreName)

    // region Provider methods
    fun createMockMovieEntity(): MovieEntity =
        MovieEntity(
            mockMovieId, mockMovieTitle, mockMoviePopularity, mockMoviePosterPath, mockMovieReleaseDate, mockMovieGenreIds
        )

    fun createMockMovie(): Movie =
        Movie(
            mockMovieId, mockMovieTitle, mockMoviePopularity, mockMoviePosterPath, mockMovieReleaseDate, mockMovieGenreNames
        )

    fun createGenreEntity(): GenreEntity = GenreEntity(mockGenreId, mockGenreName)
    // endregion
}
