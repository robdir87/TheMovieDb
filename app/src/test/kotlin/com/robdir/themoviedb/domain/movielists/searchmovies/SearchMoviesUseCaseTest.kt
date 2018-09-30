package com.robdir.themoviedb.domain.movielists.searchmovies

import com.robdir.themoviedb.MockDataProvider.createMockGenreEntity
import com.robdir.themoviedb.MockDataProvider.createMockMovie
import com.robdir.themoviedb.MockDataProvider.createMockMovieEntity
import com.robdir.themoviedb.MockDataProvider.mockGenreId
import com.robdir.themoviedb.MockDataProvider.mockGenreName
import com.robdir.themoviedb.data.genres.GenresRepositoryContract
import com.robdir.themoviedb.data.movies.MoviesRepositoryContract
import com.robdir.themoviedb.domain.GenreNameMapper
import com.robdir.themoviedb.domain.movielists.common.MovieMapper
import com.robdir.themoviedb.mock
import io.reactivex.Single
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito.never
import org.mockito.Mockito.verify

class SearchMoviesUseCaseTest {

    private val mockMoviesRepositoryContract = mock<MoviesRepositoryContract>()
    private val mockGenreRepositoryContract = mock<GenresRepositoryContract>()
    private val mockMovieMapper = mock<MovieMapper>()
    private val mockGenreNameMapper = mock<GenreNameMapper>()

    private val searchMoviesUseCase =
        SearchMoviesUseCase(mockMoviesRepositoryContract, mockGenreRepositoryContract, mockMovieMapper, mockGenreNameMapper)

    private val genreEntities = listOf(createMockGenreEntity())
    private val queryString = "some-query-string"
    private val forceUpdate = false

    @Test
    fun `WHEN searchMovies is called AND a repository fails THEN verify an exception is returned`() {
        // Arrange
        given(mockMoviesRepositoryContract.searchMovies(queryString))
            .willReturn(Single.error(Exception()))
        given(mockGenreRepositoryContract.getGenres(forceUpdate))
            .willReturn(Single.just(genreEntities))

        // Act & Assert
        searchMoviesUseCase.searchMovies(queryString)
            .test()
            .assertError(Exception::class.java)

        verify(mockGenreRepositoryContract).getGenres(forceUpdate = false)
        verify(mockGenreRepositoryContract, never()).getGenres(forceUpdate = true)
    }

    @Test
    fun `WHEN searchMovies is called AND movies and genres are retrieved from the repositories THEN verify mapped domain movies are returned sorted by popularity`() {
        // Arrange
        val firstMovieId = 123
        val firstMoviePopularity = 10.0
        val secondMovieId = 456
        val secondMoviePopularity = 12.0

        val movieEntities = listOf(
            createMockMovieEntity(id = firstMovieId, popularity = firstMoviePopularity),
            createMockMovieEntity(id = secondMovieId, popularity = secondMoviePopularity)
        )

        val sortedMovieEntities = listOf(
            createMockMovieEntity(id = secondMovieId, popularity = secondMoviePopularity),
            createMockMovieEntity(id = firstMovieId, popularity = firstMoviePopularity)
        )

        val movies = listOf(
            createMockMovie(id = secondMovieId, popularity = secondMoviePopularity),
            createMockMovie(id = firstMovieId, popularity = firstMoviePopularity)
        )
        val genreNameMap = mapOf(mockGenreId to mockGenreName)

        given(mockMoviesRepositoryContract.searchMovies(queryString))
            .willReturn(Single.just(movieEntities))
        given(mockGenreRepositoryContract.getGenres(forceUpdate))
            .willReturn(Single.just(genreEntities))
        given(mockMovieMapper.toDomainModel(sortedMovieEntities, genreNameMap))
            .willReturn(movies)
        given(mockGenreNameMapper.toNameMap(genreEntities))
            .willReturn(genreNameMap)

        // Act & Assert
        searchMoviesUseCase.searchMovies(queryString)
            .test()
            .assertResult(movies)
    }
}
