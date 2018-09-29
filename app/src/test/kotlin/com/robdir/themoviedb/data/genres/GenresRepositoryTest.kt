package com.robdir.themoviedb.data.genres

import com.robdir.themoviedb.MockDataProvider.createMockGenreEntity
import com.robdir.themoviedb.data.MovieApi
import com.robdir.themoviedb.data.persistence.GenresDao
import com.robdir.themoviedb.mock
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.BDDMockito
import org.mockito.BDDMockito.doReturn
import org.mockito.BDDMockito.given
import org.mockito.Mockito.never
import org.mockito.Mockito.spy
import org.mockito.Mockito.verify

class GenresRepositoryTest {

    private val mockMovieApi = mock<MovieApi>()
    private val mockGenresDao = mock<GenresDao>()
    private val genreRepository = spy(GenresRepository(mockMovieApi, mockGenresDao))

    private val genreTestObserver = TestObserver<List<GenreEntity>>()
    private val inOrder = BDDMockito.inOrder(mockGenresDao, mockMovieApi, genreRepository)

    private val genreList: List<GenreEntity> = listOf(createMockGenreEntity())
    private val getGenresResponse = GetGenresResponseEntity(genreList)

    // region getGenres
    @Test
    fun `WHEN getGenres is called AND forceUpdate is true THEN verify genres are fetched via the api`() {
        // Arrange
        doReturn(Single.just(genreList))
            .`when`(genreRepository).getGenresFromApi()

        // Act
        genreRepository.getGenres(forceUpdate = true).subscribe(genreTestObserver)

        // Assert
        genreTestObserver.assertResult(genreList)
        verify(mockGenresDao).deleteGenres()
        verify(genreRepository).getGenresFromApi()
        verify(mockGenresDao, never()).getGenres()
    }

    @Test
    fun `WHEN getGenres is called AND forceUpdate is false AND database fetch fails THEN verify genres are fetched via the api`() {
        // Arrange
        given(mockGenresDao.getGenres())
            .willReturn(Single.error(Exception()))
        doReturn(Single.just(genreList))
            .`when`(genreRepository).getGenresFromApi()

        // Act
        genreRepository.getGenres(forceUpdate = false).subscribe(genreTestObserver)

        // Assert
        genreTestObserver.assertResult(genreList)
        inOrder.run {
            verify(mockGenresDao, never()).deleteGenres()
            verify(mockGenresDao).getGenres()
            verify(genreRepository).getGenresFromApi()
        }
    }

    @Test
    fun `WHEN getGenres is called AND forceUpdate is false AND database contains no genres THEN verify genres are fetched via the api`() {
        // Arrange
        given(mockGenresDao.getGenres())
            .willReturn(Single.just(emptyList()))
        doReturn(Single.just(genreList))
            .`when`(genreRepository).getGenresFromApi()

        // Act
        genreRepository.getGenres(forceUpdate = false).subscribe(genreTestObserver)

        // Assert
        genreTestObserver.assertResult(genreList)
        inOrder.run {
            verify(mockGenresDao, never()).deleteGenres()
            verify(mockGenresDao).getGenres()
            verify(genreRepository).getGenresFromApi()
        }
    }

    @Test
    fun `WHEN getGenres is called AND forceUpdate is false AND database contains genres THEN verify genres are fetched via the api`() {
        // Arrange
        given(mockGenresDao.getGenres())
            .willReturn(Single.just(genreList))

        // Act
        genreRepository.getGenres(forceUpdate = false).subscribe(genreTestObserver)

        // Assert
        genreTestObserver.assertResult(genreList)
        inOrder.run {
            verify(mockGenresDao, never()).deleteGenres()
            verify(mockGenresDao).getGenres()
            verify(genreRepository, never()).getGenresFromApi()
        }
    }
    // endregion

    // region getGenresFromApi
    @Test
    fun `WHEN getGenresFromApi is called THEN verify genres are fetched via the api and saved in the database`() {
        // Arrange
        given(mockMovieApi.getGenres(apiKey = anyString(), language = anyString()))
            .willReturn(Single.just(getGenresResponse))

        // Act
        genreRepository.getGenresFromApi().subscribe(genreTestObserver)

        // Assert
        genreTestObserver.assertValue(genreList)
        inOrder.run {
            verify(mockMovieApi).getGenres()
            verify(mockGenresDao).saveGenres(genreList)
        }
    }
    // endregion
}
