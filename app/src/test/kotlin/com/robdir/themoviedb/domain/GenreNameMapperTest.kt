package com.robdir.themoviedb.domain

import com.robdir.themoviedb.MockDataProvider.mockAnotherGenreId
import com.robdir.themoviedb.MockDataProvider.mockAnotherGenreName
import com.robdir.themoviedb.MockDataProvider.mockGenreId
import com.robdir.themoviedb.MockDataProvider.mockGenreName
import com.robdir.themoviedb.data.genres.GenreEntity
import org.junit.Assert.assertEquals
import org.junit.Test

class GenreNameMapperTest {

    private val genreNameMapper = GenreNameMapper()

    // region toNameList
    @Test
    fun `WHEN toNameList is called AND genreMap does not contain any of the genreIds THEN verify an empty list is returned`() {
        // Arrange
        val genreIds = listOf(mockGenreId)
        val genreMap = mapOf(mockAnotherGenreId to mockAnotherGenreName)

        // Act & Assert
        assertEquals(
            emptyList<String>(),
            genreNameMapper.toNameList(genreIds, genreMap)
        )
    }

    @Test
    fun `WHEN toNameList is called AND genreMap contains at least one of the genreIds THEN verify a list of names is returned`() {
        // Arrange
        val genreIds = listOf(mockGenreId, mockAnotherGenreId)
        val genreMap = mapOf(mockAnotherGenreId to mockAnotherGenreName)

        // Act & Assert
        assertEquals(
            listOf(mockAnotherGenreName),
            genreNameMapper.toNameList(genreIds, genreMap)
        )
    }
    // endregion

    // region toNameMap
    @Test
    fun `WHEN toNameMap is called THEN verify a mapping of ids into names is returned`() {
        // Arrange
        val genres = listOf(
            GenreEntity(mockGenreId, mockGenreName), GenreEntity(mockAnotherGenreId, mockAnotherGenreName)
        )

        // Act & Assert
        assertEquals(
            mapOf(mockGenreId to mockGenreName, mockAnotherGenreId to mockAnotherGenreName),
            genreNameMapper.toNameMap(genres)
        )
    }
    // endregion
}
