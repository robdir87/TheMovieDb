package com.robdir.themoviedb.presentation.moviedetails

import android.arch.lifecycle.Observer
import com.robdir.themoviedb.MockDataProvider.createMockMovieDetail
import com.robdir.themoviedb.MockDataProvider.createMockMovieDetailModel
import com.robdir.themoviedb.MockDataProvider.mockMovieId
import com.robdir.themoviedb.domain.moviedetails.GetMovieDetailsContract
import com.robdir.themoviedb.mock
import com.robdir.themoviedb.presentation.base.BaseViewModel
import com.robdir.themoviedb.presentation.common.BaseViewModelTest
import io.reactivex.Single
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.BDDMockito
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import java.io.IOException

class MovieDetailViewModelTest : BaseViewModelTest() {

    private val mockGetMovieDetailsContract = mock<GetMovieDetailsContract>()
    private val mockMovieDetailModelMapper = mock<MovieDetailModelMapper>()

    private val movieDetailViewModel = Mockito.spy(
        MovieDetailViewModel(
            mockGetMovieDetailsContract,
            mockMovieDetailModelMapper,
            mockNetworkInfoProvider,
            testSchedulerProvider
        )
    )

    private val mockMovieDetailObserver = mock<Observer<MovieDetailModel>>()

    private val inOrder = BDDMockito.inOrder(
        mockIsLoadingObserver,
        mockMovieDetailObserver,
        mockErrorObserver,
        mockNetworkErrorObserver,
        movieDetailViewModel
    )

    override fun setup() {
        super.setup()
        with(movieDetailViewModel) {
            isLoading.observeForever(mockIsLoadingObserver)
            movieDetail.observeForever(mockMovieDetailObserver)
        }
    }

    override fun getViewModel(): BaseViewModel = movieDetailViewModel

    @Test
    fun `WHEN getMovieDetail is called AND movie detail is retrieved THEN verify movieDetail is updated`() {
        // Arrange
        val mockMovieDetail = createMockMovieDetail()
        val mockMovieDetailModel = createMockMovieDetailModel()

        given(mockGetMovieDetailsContract.getMovieDetail(mockMovieId))
            .willReturn(Single.just(mockMovieDetail))
        given(mockMovieDetailModelMapper.toPresentationModel(mockMovieDetail))
            .willReturn(mockMovieDetailModel)

        // Act
        movieDetailViewModel.getMovieDetail(mockMovieId)

        // Assert
        inOrder.run {
            verify(mockIsLoadingObserver).onChanged(true)
            verify(mockIsLoadingObserver).onChanged(false)
            verify(mockMovieDetailObserver).onChanged(mockMovieDetailModel)
        }

        assertEquals(mockMovieDetailModel, movieDetailViewModel.movieDetail.value)
    }

    @Test
    fun `WHEN getMovieDetail is called AND an IOException is thrown AND there is no network available THEN verify networkError is updated`() {
        // Arrange
        given(mockGetMovieDetailsContract.getMovieDetail(mockMovieId))
            .willReturn(Single.error(IOException()))
        given(mockNetworkInfoProvider.isNetworkAvailable())
            .willReturn(false)

        // Act
        movieDetailViewModel.getMovieDetail(mockMovieId)

        // Assert
        inOrder.run {
            verify(mockIsLoadingObserver).onChanged(true)
            verify(mockIsLoadingObserver).onChanged(false)
            verify(mockNetworkErrorObserver).onChanged(error)
        }
        assertEquals(error, movieDetailViewModel.networkError.value)
    }

    @Test
    fun `WHEN getMovieDetail is called AND an IOException is thrown AND there is a network available THEN verify error is updated`() {
        // Arrange
        given(mockGetMovieDetailsContract.getMovieDetail(mockMovieId))
            .willReturn(Single.error(IOException()))
        given(mockNetworkInfoProvider.isNetworkAvailable())
            .willReturn(true)

        // Act & Assert
        callGetMovieDetailAndAssertErrorUpdated()
    }

    @Test
    fun `WHEN getMovieDetail is called AND an Exception that is not IO is thrown THEN verify error is updated`() {
        // Arrange
        given(mockGetMovieDetailsContract.getMovieDetail(mockMovieId))
            .willReturn(Single.error(Exception()))

        // Act & Assert
        callGetMovieDetailAndAssertErrorUpdated()
    }

    private fun callGetMovieDetailAndAssertErrorUpdated() {
        // Act
        movieDetailViewModel.getMovieDetail(mockMovieId)

        // Assert
        inOrder.run {
            verify(mockIsLoadingObserver).onChanged(true)
            verify(mockIsLoadingObserver).onChanged(false)
            verify(mockErrorObserver).onChanged(error)
        }
        Assert.assertEquals(error, movieDetailViewModel.error.value)
    }
}
