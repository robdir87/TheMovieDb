package com.robdir.themoviedb.presentation.movielists.common

import com.robdir.themoviedb.MockDataProvider.createMockMovie
import com.robdir.themoviedb.MockDataProvider.createMockMovieModel
import com.robdir.themoviedb.MockDataProvider.mockAnotherGenreName
import com.robdir.themoviedb.MockDataProvider.mockGenreName
import com.robdir.themoviedb.MockDataProvider.mockMoviePosterBaseUrl
import org.junit.Assert.assertEquals
import org.junit.Test

class MovieModelMapperTest {

    private val movieModelMapper = MovieModelMapper(mockMoviePosterBaseUrl)

    @Test
    fun `WHEN toPresentationModel is called AND a Movie is received AND genreNames contains a single genre THEN verify a MovieModel is returned`() {
        // Arrange, Act & Assert
        assertEquals(createMockMovieModel(), movieModelMapper.toPresentationModel(createMockMovie()))
    }

    @Test
    fun `WHEN toPresentationModel is called AND a Movie is received AND genreNames contains multiple genres THEN verify a MovieModel is returned`() {
        // Arrange, Act & Assert
        assertEquals(createMockMovieModel("$mockGenreName, $mockAnotherGenreName"), movieModelMapper.toPresentationModel(createMockMovie(listOf(mockGenreName, mockAnotherGenreName))))
    }

    @Test
    fun `WHEN toPresentationModel is called AND a list of Movie is received THEN verify a list of MovieModel is returned`() {
        // Arrange, Act & Assert
        assertEquals(listOf(createMockMovieModel()), listOf(movieModelMapper.toPresentationModel(createMockMovie())))
    }
}
