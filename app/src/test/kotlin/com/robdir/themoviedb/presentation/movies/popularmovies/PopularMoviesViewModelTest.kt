package com.robdir.themoviedb.presentation.movies.popularmovies

import com.robdir.themoviedb.MockDataProvider.createMockMovie
import com.robdir.themoviedb.MockDataProvider.createMockMovieModel
import com.robdir.themoviedb.TestSchedulerProvider
import com.robdir.themoviedb.domain.popularmovies.GetPopularMoviesContract
import com.robdir.themoviedb.mock
import com.robdir.themoviedb.presentation.movies.common.MovieModelMapper
import io.reactivex.Single
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.BDDMockito.given

class PopularMoviesViewModelTest {

    private val mockGetPopularMoviesContract = mock<GetPopularMoviesContract>()
    private val mockMovieModelMapper = mock<MovieModelMapper>()
    private val testSchedulerProvider = TestSchedulerProvider()

    private val popularMovieViewModel = PopularMoviesViewModel(
        mockGetPopularMoviesContract, mockMovieModelMapper, testSchedulerProvider
    )

    @Test
    fun `WHEN getPopularMovies is called THEN verify movies are updated`() {
        // Arrange
        val mockMovieList = listOf(createMockMovie())
        val mockMovieModelList = listOf(createMockMovieModel())

        given(mockGetPopularMoviesContract.getPopularMovies())
            .willReturn(Single.just(mockMovieList))
        given(mockMovieModelMapper.toPresentationModel(mockMovieList))
            .willReturn(mockMovieModelList)

        // Act
        popularMovieViewModel.getPopularMovies()

        // Assert
        popularMovieViewModel.isLoading.observeForever { isLoading ->
        }
        popularMovieViewModel.movies.observeForever { movies ->
            assertEquals(1, movies?.size)
        }
    }
}
