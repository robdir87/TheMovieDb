package com.robdir.themoviedb.domain.moviedetails

import com.robdir.themoviedb.MockDataProvider.createMockMovieDetail
import com.robdir.themoviedb.MockDataProvider.createMockMovieDetailEntity
import com.robdir.themoviedb.MockDataProvider.mockMovieId
import com.robdir.themoviedb.data.movies.MoviesRepositoryContract
import com.robdir.themoviedb.mock
import io.reactivex.Single
import org.junit.Test
import org.mockito.BDDMockito.given

class GetMovieDetailsUseCaseTest {

    private val mockMoviesRepositoryContract = mock<MoviesRepositoryContract>()
    private val mockMovieDetailMapper = mock<MovieDetailMapper>()

    private val getMovieDetailsUseCase = GetMovieDetailsUseCase(
        mockMoviesRepositoryContract,
        mockMovieDetailMapper
    )

    @Test
    fun `WHEN getMovieDetail is called AND the repository fails THEN verify an Exception is thrown`() {
        // Arrange
        given(mockMoviesRepositoryContract.getMovieDetails(mockMovieId))
            .willReturn(Single.error(Exception()))

        // Act & Assert
        getMovieDetailsUseCase.getMovieDetail(mockMovieId)
            .test()
            .assertError(Exception::class.java)
    }

    @Test
    fun `WHEN getMovieDetail is called AND the repository fails THEN verify mapped domain movie detail model is returned`() {
        // Arrange
        val movieDetailEntity = createMockMovieDetailEntity()
        val movieDetail = createMockMovieDetail()

        given(mockMoviesRepositoryContract.getMovieDetails(mockMovieId))
            .willReturn(Single.just(movieDetailEntity))
        given(mockMovieDetailMapper.toDomainModel(movieDetailEntity))
            .willReturn(movieDetail)

        // Act & Assert
        getMovieDetailsUseCase.getMovieDetail(mockMovieId)
            .test()
            .assertResult(movieDetail)
    }
}
