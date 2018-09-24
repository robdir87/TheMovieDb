package com.robdir.themoviedb.data.movies

import com.robdir.themoviedb.data.MovieApi
import com.robdir.themoviedb.mock
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.BDDMockito.given

class MoviesRepositoryTest {

    private val mockMovieApi = mock<MovieApi>()
    private val popularMoviesRepository = MoviesRepository(mockMovieApi)

    @Test
    fun `WHEN getPopularMovies is called THEN verify popular movies are fetched via the api`() {
        // Arrange
        val popularMoviesTestObserver = TestObserver<List<MovieEntity>>()
        val page = 1
        val popularMovieList: List<MovieEntity> = emptyList()
        val searchResult = SearchResultEntity(
            page = page, totalResults = 0, results = popularMovieList, totalPages = 1
        )

        given(mockMovieApi.getPopularMovies(apiKey = anyString(), language = anyString(), page = anyInt()))
            .willReturn(Single.just(searchResult))

        // Act
        popularMoviesRepository.getPopularMovies(pageNumber = page).subscribe(popularMoviesTestObserver)

        // Assert
        popularMoviesTestObserver.assertResult(popularMovieList)
    }
}
