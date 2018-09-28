package com.robdir.themoviedb.presentation.movielists.popularmovies

import android.arch.lifecycle.Observer
import com.robdir.themoviedb.MockDataProvider.createMockMovie
import com.robdir.themoviedb.MockDataProvider.createMockMovieModel
import com.robdir.themoviedb.domain.movielists.popularmovies.GetPopularMoviesContract
import com.robdir.themoviedb.mock
import com.robdir.themoviedb.presentation.base.BaseViewModel
import com.robdir.themoviedb.presentation.common.BaseViewModelTest
import com.robdir.themoviedb.presentation.movielists.common.MovieModel
import com.robdir.themoviedb.presentation.movielists.common.MovieModelMapper
import io.reactivex.Single
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.inOrder
import org.mockito.Mockito.spy
import java.io.IOException

class PopularMoviesViewModelTest : BaseViewModelTest() {

    private val mockGetPopularMoviesContract = mock<GetPopularMoviesContract>()
    private val mockMovieModelMapper = mock<MovieModelMapper>()

    private val forceUpdate = false

    private val popularMovieViewModel = spy(
        PopularMoviesViewModel(
            mockGetPopularMoviesContract,
            mockMovieModelMapper,
            mockNetworkInfoProvider,
            testSchedulerProvider
        )
    )

    private val mockMoviesObserver = mock<Observer<List<MovieModel>>>()

    private val inOrder = inOrder(
        mockIsLoadingObserver,
        mockMoviesObserver,
        mockErrorObserver,
        mockNetworkErrorObserver,
        popularMovieViewModel
    )

    override fun setup() {
        super.setup()
        with(popularMovieViewModel) {
            isLoading.observeForever(mockIsLoadingObserver)
            movies.observeForever(mockMoviesObserver)
        }
    }

    override fun getViewModel(): BaseViewModel = popularMovieViewModel

    @Test
    fun `WHEN getPopularMovies is called AND retrieved movie list is not empty THEN verify movies are updated`() {
        // Arrange
        val mockMovieList = listOf(createMockMovie())
        val mockMovieModelList = listOf(createMockMovieModel())

        given(mockGetPopularMoviesContract.getPopularMovies(forceUpdate))
            .willReturn(Single.just(mockMovieList))
        given(mockMovieModelMapper.toPresentationModel(mockMovieList))
            .willReturn(mockMovieModelList)

        // Act
        popularMovieViewModel.getPopularMovies(forceUpdate)

        // Assert
        inOrder.run {
            verify(mockIsLoadingObserver).onChanged(true)
            verify(mockIsLoadingObserver).onChanged(false)
            verify(mockMoviesObserver).onChanged(mockMovieModelList)
        }

        assertEquals(mockMovieModelList, popularMovieViewModel.movies.value)
    }

    @Test
    fun `WHEN getPopularMovies is called AND retrieved movie list is empty THEN verify error is updated`() {
        // Arrange
        given(mockGetPopularMoviesContract.getPopularMovies(forceUpdate))
            .willReturn(Single.just(emptyList()))
        given(mockMovieModelMapper.toPresentationModel(emptyList()))
            .willReturn(emptyList())

        // Act & Assert
        callGetPopularMoviesAndAssertErrorUpdated()
    }

    @Test
    fun `WHEN getPopularMovies is called AND an IOException is thrown AND there is no network available THEN verify networkError is updated`() {
        // Arrange
        given(mockGetPopularMoviesContract.getPopularMovies(forceUpdate))
            .willReturn(Single.error(IOException()))
        given(mockNetworkInfoProvider.isNetworkAvailable())
            .willReturn(false)

        // Act
        popularMovieViewModel.getPopularMovies(forceUpdate)

        // Assert
        inOrder.run {
            verify(mockIsLoadingObserver).onChanged(true)
            verify(mockIsLoadingObserver).onChanged(false)
            verify(mockNetworkErrorObserver).onChanged(error)
        }
        assertEquals(error, popularMovieViewModel.networkError.value)
    }

    @Test
    fun `WHEN getPopularMovies is called AND an IOException is thrown AND there is a network available THEN verify error is updated`() {
        // Arrange
        given(mockGetPopularMoviesContract.getPopularMovies(forceUpdate))
            .willReturn(Single.error(IOException()))
        given(mockNetworkInfoProvider.isNetworkAvailable())
            .willReturn(true)

        // Act & Assert
        callGetPopularMoviesAndAssertErrorUpdated()
    }

    @Test
    fun `WHEN getPopularMovies is called AND an Exception that is not IO is thrown THEN verify error is updated`() {
        // Arrange
        given(mockGetPopularMoviesContract.getPopularMovies(forceUpdate))
            .willReturn(Single.error(Exception()))

        // Act & Assert
        callGetPopularMoviesAndAssertErrorUpdated()
    }

    private fun callGetPopularMoviesAndAssertErrorUpdated() {
        // Act
        popularMovieViewModel.getPopularMovies(forceUpdate)

        // Assert
        inOrder.run {
            verify(mockIsLoadingObserver).onChanged(true)
            verify(mockIsLoadingObserver).onChanged(false)
            verify(mockErrorObserver).onChanged(error)
        }
        assertEquals(error, popularMovieViewModel.error.value)
    }
}
