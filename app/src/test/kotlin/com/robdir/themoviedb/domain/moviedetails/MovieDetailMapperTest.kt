package com.robdir.themoviedb.domain.moviedetails

import com.robdir.themoviedb.MockDataProvider.createMockMovieDetail
import com.robdir.themoviedb.MockDataProvider.createMockMovieDetailEntity
import org.junit.Assert.assertEquals
import org.junit.Test

class MovieDetailMapperTest {

    private val movieDetailMapper = MovieDetailMapper()

    @Test
    fun `WHEN toDomainModel is called AND a MovieDetailEntity is received THEN a MovieDetail is returned`() {
        // Arrange, Act & Assert
        assertEquals(createMockMovieDetail(), movieDetailMapper.toDomainModel(createMockMovieDetailEntity()))
    }
}
