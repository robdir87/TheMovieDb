package com.robdir.themoviedb.presentation.moviedetails

import com.robdir.themoviedb.MockDataProvider.createMockMovieDetail
import com.robdir.themoviedb.MockDataProvider.createMockMovieDetailModel
import org.junit.Assert.assertEquals
import org.junit.Test

class MovieDetailModelMapperTest {

    private val movieModelMapper = MovieDetailModelMapper()

    @Test
    fun `WHEN toPresentationModel is called AND a MovieDetail is received THEN verify a MovieDetailModel is returned`() {
        // Arrange, Act & Assert
        assertEquals(createMockMovieDetailModel(), movieModelMapper.toPresentationModel(createMockMovieDetail()))
    }
}
