package com.robdir.themoviedb.data.movies

import com.robdir.themoviedb.MockDataProvider.createMockMovieDetailEntity
import com.robdir.themoviedb.MockDataProvider.mockMovieId
import com.robdir.themoviedb.data.MovieApi
import com.robdir.themoviedb.mock
import io.reactivex.Single
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.BDDMockito.given

class MoviesRepositoryTest {

    private val mockMovieApi = mock<MovieApi>()
    private val moviesRepository = MoviesRepository(mockMovieApi)

    @Test
    fun `WHEN getPopularMovies is called THEN verify popular movies are fetched via the api`() {
        // Arrange
        val page = 1
        val popularMovieList: List<MovieEntity> = emptyList()
        val searchResult = SearchResultEntity(
            page = page, totalResults = 0, results = popularMovieList, totalPages = 1
        )

        given(mockMovieApi.getPopularMovies(apiKey = anyString(), language = anyString(), page = anyInt()))
            .willReturn(Single.just(searchResult))

        // Act & Assert
        moviesRepository.getPopularMovies(pageNumber = page)
            .test()
            .assertResult(popularMovieList)
    }

    @Test
    fun `WHEN getMovieDetails is called THEN verify movie details are fetched via the api`() {
        // Arrange
        var movieDetail = createMockMovieDetailEntity()

        given(mockMovieApi.getMovieDetails(movieId = mockMovieId))
            .willReturn(Single.just(movieDetail))

        // Act & Assert
        moviesRepository.getMovieDetails(mockMovieId)
            .test()
            .assertResult(movieDetail)
    }
}
