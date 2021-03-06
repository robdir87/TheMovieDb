package com.robdir.themoviedb.data.movies

import com.robdir.themoviedb.MockDataProvider.createMockMovieDetailEntity
import com.robdir.themoviedb.MockDataProvider.createMockMovieEntity
import com.robdir.themoviedb.MockDataProvider.mockMovieId
import com.robdir.themoviedb.data.MovieApi
import com.robdir.themoviedb.data.persistence.PopularMoviesDao
import com.robdir.themoviedb.mock
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.inOrder
import org.mockito.Mockito.spy
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.verify
import org.mockito.Mockito.never

class MoviesRepositoryTest {

    private val mockMovieApi = mock<MovieApi>()
    private val mockPopularMoviesDao = mock<PopularMoviesDao>()
    private val moviesRepository = spy(MoviesRepository(mockMovieApi, mockPopularMoviesDao))

    private val popularMoviesTestObserver = TestObserver<List<MovieEntity>>()
    private val inOrder = inOrder(mockPopularMoviesDao, mockMovieApi, moviesRepository)

    private val page = 1
    private val movieList: List<MovieEntity> = listOf(createMockMovieEntity())
    private val searchResult = SearchResultEntity(
        page = page, totalResults = 1, results = movieList, totalPages = 1
    )

    // region getPopularMovies
    @Test
    fun `WHEN getPopularMovies is called AND forceUpdate is true THEN verify popular movies are fetched via the api`() {
        // Arrange
        doReturn(Single.just(movieList))
            .`when`(moviesRepository).getPopularMoviesFromApi()

        // Act
        moviesRepository.getPopularMovies(forceUpdate = true).subscribe(popularMoviesTestObserver)

        // Assert
        popularMoviesTestObserver.assertResult(movieList)
        verify(mockPopularMoviesDao).deleteMovies()
        verify(moviesRepository).getPopularMoviesFromApi()
        verify(mockPopularMoviesDao, never()).getPopularMovies()
    }

    @Test
    fun `WHEN getPopularMovies is called AND forceUpdate is false AND database fetch fails THEN verify popular movies are fetched via the api`() {
        // Arrange
        given(mockPopularMoviesDao.getPopularMovies())
            .willReturn(Single.error(Exception()))
        doReturn(Single.just(movieList))
            .`when`(moviesRepository).getPopularMoviesFromApi()

        // Act
        moviesRepository.getPopularMovies(forceUpdate = false).subscribe(popularMoviesTestObserver)

        // Assert
        popularMoviesTestObserver.assertResult(movieList)
        inOrder.run {
            verify(mockPopularMoviesDao, never()).deleteMovies()
            verify(mockPopularMoviesDao).getPopularMovies()
            verify(moviesRepository).getPopularMoviesFromApi()
        }
    }

    @Test
    fun `WHEN getPopularMovies is called AND forceUpdate is false AND database contains no popular movies THEN verify popular movies are fetched via the api`() {
        // Arrange
        given(mockPopularMoviesDao.getPopularMovies())
            .willReturn(Single.just(emptyList()))
        doReturn(Single.just(movieList))
            .`when`(moviesRepository).getPopularMoviesFromApi()

        // Act
        moviesRepository.getPopularMovies(forceUpdate = false).subscribe(popularMoviesTestObserver)

        // Assert
        popularMoviesTestObserver.assertResult(movieList)
        inOrder.run {
            verify(mockPopularMoviesDao, never()).deleteMovies()
            verify(mockPopularMoviesDao).getPopularMovies()
            verify(moviesRepository).getPopularMoviesFromApi()
        }
    }

    @Test
    fun `WHEN getPopularMovies is called AND forceUpdate is false AND database contains popular movies THEN verify popular movies are fetched via the api`() {
        // Arrange
        given(mockPopularMoviesDao.getPopularMovies())
            .willReturn(Single.just(movieList))

        // Act
        moviesRepository.getPopularMovies(forceUpdate = false).subscribe(popularMoviesTestObserver)

        // Assert
        popularMoviesTestObserver.assertResult(movieList)
        inOrder.run {
            verify(mockPopularMoviesDao, never()).deleteMovies()
            verify(mockPopularMoviesDao).getPopularMovies()
            verify(moviesRepository, never()).getPopularMoviesFromApi()
        }
    }
    // endregion

    // region getPopularMoviesFromApi
    @Test
    fun `WHEN getPopularMoviesFromApi is called THEN verify movies are fetched via the api and saved in the database`() {
        // Arrange
        given(mockMovieApi.getPopularMovies(apiKey = anyString(), language = anyString(), page = anyInt()))
            .willReturn(Single.just(searchResult))

        // Act
        moviesRepository.getPopularMoviesFromApi().subscribe(popularMoviesTestObserver)

        // Assert
        popularMoviesTestObserver.assertValue(movieList)
        inOrder.run {
            verify(mockMovieApi).getPopularMovies()
            verify(mockPopularMoviesDao).savePopularMovies(movieList)
        }
    }
    // endregion

    // region getMovieDetails
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
    // endregion

    // region searchMovies
    @Test
    fun `WHEN searchMovies is called THEN verify movies are fetched via the api`() {
        // Arrange
        val query = "some-query"
        val searchResult = SearchResultEntity(
            page = page, totalResults = 1, totalPages = 1, results = movieList
        )

        given(mockMovieApi.searchMovies(query = query))
            .willReturn(Single.just(searchResult))

        // Act & Assert
        moviesRepository.searchMovies(query)
            .test()
            .assertResult(movieList)
    }
    // endregion
}
