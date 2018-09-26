package com.robdir.themoviedb.domain.moviedetails

import com.robdir.themoviedb.MockDataProvider.createMockLanguageEntity
import com.robdir.themoviedb.MockDataProvider.createMockMovieDetail
import com.robdir.themoviedb.MockDataProvider.createMockMovieDetailEntity
import com.robdir.themoviedb.MockDataProvider.mockLanguageName
import com.robdir.themoviedb.data.movies.LanguageEntity
import org.junit.Assert.assertEquals
import org.junit.Test

class MovieDetailMapperTest {

    private val movieDetailMapper = MovieDetailMapper()

    @Test
    fun `WHEN toDomainModel is called AND a MovieDetailEntity is received THEN a MovieDetail is returned`() {
        // Arrange, Act & Assert
        assertEquals(createMockMovieDetail(), movieDetailMapper.toDomainModel(createMockMovieDetailEntity()))
    }

    @Test
    fun `WHEN toDomainModel is called AND a MovieDetailEntity is received AND spokenLanguages contains a language with no name THEN a MovieDetail is returned AND the language with no name is discarded`() {
        // Arrange, Act & Assert
        assertEquals(
            createMockMovieDetail(spokenLanguages = listOf(mockLanguageName)),
            movieDetailMapper.toDomainModel(createMockMovieDetailEntity(listOf(LanguageEntity("some-code", ""), createMockLanguageEntity())))
        )
    }
}
