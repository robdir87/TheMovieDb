package com.robdir.themoviedb

import com.robdir.themoviedb.data.genres.GenreEntity
import com.robdir.themoviedb.data.movies.LanguageEntity
import com.robdir.themoviedb.data.movies.MovieDetailEntity
import com.robdir.themoviedb.data.movies.MovieEntity
import com.robdir.themoviedb.domain.moviedetails.MovieDetail
import com.robdir.themoviedb.domain.movielists.common.Movie
import com.robdir.themoviedb.presentation.moviedetails.MovieDetailModel
import com.robdir.themoviedb.presentation.movielists.common.MovieModel
import com.robdir.themoviedb.presentation.movielists.common.POSTER_WIDTH
import com.robdir.themoviedb.presentation.movielists.common.THUMBNAIL_WIDTH

object MockDataProvider {
    // Genre
    const val mockGenreId = 0
    const val mockGenreName = "Horror"
    const val mockAnotherGenreId = 1
    const val mockAnotherGenreName = "Fantasy"

    // Language
    const val mockLanguageCode = "en"
    const val mockLanguageName = "English"

    // Movie
    const val mockMovieId = 123
    private const val mockMovieTitle = "Some horror movie"
    private const val mockMoviePopularity = 7.1
    private const val mockRoundedPopularity = 7
    private const val mockMoviePosterPath = "some-poster-path"
    private const val mockMovieReleaseDate = "2018-03-15"
    private const val mockMovieReleaseYear = "2018"
    const val mockMoviePosterBaseUrl = "http://some-base-url.com/w"
    private const val mockMoviePosterUrl = "http://some-base-url.com/w$POSTER_WIDTH/$mockMoviePosterPath"
    private const val mockMovieThumbnailUrl = "http://some-base-url.com/w$THUMBNAIL_WIDTH/$mockMoviePosterPath"

    private const val mockMovieHomepage = "http://some-homepage.com"
    private const val mockMovieOverview = "mock overview"
    private const val mockMovieRuntime = 100
    private const val mockMovieRevenue = 100000

    val mockMovieGenreIds = listOf(mockGenreId)
    val mockMovieGenreNames = listOf(mockGenreName)
    val mockMovieGenreMap = mapOf(mockGenreId to mockGenreName, mockAnotherGenreId to mockAnotherGenreName)

    // region Provider methods
    fun createMockMovieEntity() = MovieEntity(
        mockMovieId,
        mockMovieTitle,
        mockMoviePopularity,
        mockMoviePosterPath,
        mockMovieReleaseDate,
        mockMovieGenreIds
    )

    fun createMockMovie() = Movie(
        mockMovieId,
        mockMovieTitle,
        mockMoviePopularity,
        mockMoviePosterPath,
        mockMovieReleaseDate,
        mockMovieGenreNames
    )

    fun createMockGenreEntity() = GenreEntity(mockGenreId, mockGenreName)

    fun createMockMovieModel(genres: List<String> = mockMovieGenreNames) = MovieModel(
        mockMovieId,
        mockMovieTitle,
        mockRoundedPopularity,
        mockMovieThumbnailUrl,
        mockMoviePosterUrl,
        mockMovieReleaseYear,
        genres
    )

    fun createMockLanguageEntity(): LanguageEntity = LanguageEntity(mockLanguageCode, mockLanguageName)

    fun createMockMovieDetailEntity(
        spokenLanguages: List<LanguageEntity> = listOf(createMockLanguageEntity())
    ) = MovieDetailEntity(
        mockMovieId,
        mockMovieHomepage,
        mockMovieOverview,
        mockMovieRuntime,
        mockMovieRevenue,
        spokenLanguages
    )

    fun createMockMovieDetail(spokenLanguages: List<String> = listOf(mockLanguageName)) = MovieDetail(
        mockMovieId,
        mockMovieHomepage,
        mockMovieOverview,
        mockMovieRuntime,
        mockMovieRevenue,
        spokenLanguages
    )

    fun createMockMovieDetailModel() = MovieDetailModel(
        mockMovieId,
        mockMovieHomepage,
        mockMovieOverview,
        mockMovieRuntime,
        mockMovieRevenue,
        listOf(mockLanguageName)
    )
    // endregion
}
