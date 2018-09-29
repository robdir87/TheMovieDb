package com.robdir.themoviedb.presentation.movielists.searchmovies

import android.arch.lifecycle.Observer
import com.robdir.themoviedb.domain.movielists.searchmovies.SearchMoviesContract
import com.robdir.themoviedb.mock
import com.robdir.themoviedb.presentation.base.BaseViewModel
import com.robdir.themoviedb.presentation.common.BaseViewModelTest
import com.robdir.themoviedb.presentation.movielists.common.MovieModel
import com.robdir.themoviedb.presentation.movielists.common.MovieModelMapper
import org.mockito.BDDMockito.given
import org.mockito.Mockito.spy
import io.reactivex.Single
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.verify
import java.io.IOException

class SearchMoviesViewModelTest : BaseViewModelTest() {

    private val mockSearchMoviesContract = mock<SearchMoviesContract>()
    private val mockMovieModelMapper = mock<MovieModelMapper>()

    private val searchMoviesViewModel = spy(
        SearchMoviesViewModel(
            mockSearchMoviesContract,
            mockMovieModelMapper,
            mockNetworkInfoProvider,
            testSchedulerProvider
        )
    )

    private val mockMoviesObserver = mock<Observer<List<MovieModel>>>()

    private val query = "some-query"

    override fun setup() {
        super.setup()
        with(searchMoviesViewModel) {
            movies.observeForever(mockMoviesObserver)
        }
    }

    override fun getViewModel(): BaseViewModel = searchMoviesViewModel

    @Test
    fun `WHEN searchMovies is called AND query is empty THEN verify movies are updated with an empty list`() {
        // Act
        searchMoviesViewModel.searchMovies("")

        // Assert
        verify(mockMoviesObserver).onChanged(emptyList())
    }

    @Test
    fun `WHEN searchMovies is called AND an IOException is thrown AND there is no network available THEN verify networkError is updated`() {
        // Arrange
        given(mockSearchMoviesContract.searchMovies(query))
            .willReturn(Single.error(IOException()))
        given(mockNetworkInfoProvider.isNetworkAvailable())
            .willReturn(false)

        // Act
        searchMoviesViewModel.searchMovies(query)

        // Assert
        verify(mockNetworkErrorObserver).onChanged(error)
        assertEquals(error, searchMoviesViewModel.networkError.value)
    }

    @Test
    fun `WHEN searchMovies is called AND an IOException is thrown AND there is a network available THEN verify error is updated`() {
        // Arrange
        given(mockSearchMoviesContract.searchMovies(query))
            .willReturn(Single.error(IOException()))
        given(mockNetworkInfoProvider.isNetworkAvailable())
            .willReturn(true)

        // Act & Assert
        callSearchMoviesAndAssertErrorUpdated()
    }

    @Test
    fun `WHEN searchMovies is called AND an Exception that is not IO is thrown THEN verify error is updated`() {
        // Arrange
        given(mockSearchMoviesContract.searchMovies(query))
            .willReturn(Single.error(Exception()))

        // Act & Assert
        callSearchMoviesAndAssertErrorUpdated()
    }

    private fun callSearchMoviesAndAssertErrorUpdated() {
        // Act
        searchMoviesViewModel.searchMovies(query)

        // Assert
        verify(mockErrorObserver).onChanged(error)
        assertEquals(error, searchMoviesViewModel.error.value)
    }
}
