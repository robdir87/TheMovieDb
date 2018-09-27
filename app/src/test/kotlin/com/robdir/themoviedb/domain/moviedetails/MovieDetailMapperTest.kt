package com.robdir.themoviedb.domain.moviedetails

import com.robdir.themoviedb.MockDataProvider.createMockMovieDetail
import com.robdir.themoviedb.MockDataProvider.createMockMovieDetailEntity
import com.robdir.themoviedb.MockDataProvider.mockOriginalLanguageCode
import com.robdir.themoviedb.MockDataProvider.mockOriginalLanguageName
import com.robdir.themoviedb.core.LocaleProvider
import com.robdir.themoviedb.mock
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given

class MovieDetailMapperTest {
    private val mockLocaleProvider = mock<LocaleProvider>()
    private val movieDetailMapper = MovieDetailMapper(mockLocaleProvider)

    @Before
    fun setup() {
        given(mockLocaleProvider.getDisplayLanguage(mockOriginalLanguageCode))
            .willReturn(mockOriginalLanguageName)
    }

    @Test
    fun `WHEN toDomainModel is called AND a MovieDetailEntity is received THEN verify a MovieDetail is returned`() {
        // Arrange, Act & Assert
        assertEquals(createMockMovieDetail(), movieDetailMapper.toDomainModel(createMockMovieDetailEntity()))
    }

    @Test
    fun `WHEN toDomainModel is called AND a MovieDetailEntity is received AND spokenLanguages contains a language with no name THEN verify a MovieDetail is returned AND the language with no name is discarded`() {
        // Arrange, Act & Assert
        assertEquals(createMockMovieDetail(), movieDetailMapper.toDomainModel(createMockMovieDetailEntity()))
    }
}
