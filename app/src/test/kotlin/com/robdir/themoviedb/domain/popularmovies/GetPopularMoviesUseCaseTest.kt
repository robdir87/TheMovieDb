package com.robdir.themoviedb.domain.popularmovies

import com.robdir.themoviedb.MockDataProvider.createGenreEntity
import com.robdir.themoviedb.MockDataProvider.createMockMovie
import com.robdir.themoviedb.MockDataProvider.createMockMovieEntity
import com.robdir.themoviedb.MockDataProvider.mockGenreId
import com.robdir.themoviedb.MockDataProvider.mockGenreName
import com.robdir.themoviedb.data.genres.GenresRepositoryContract
import com.robdir.themoviedb.data.movies.MoviesRepositoryContract
import com.robdir.themoviedb.domain.GenreNameMapperTest
import com.robdir.themoviedb.domain.movies.Movie
import com.robdir.themoviedb.domain.movies.MovieMapper
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito.mock

class GetPopularMoviesUseCaseTest {
    private val mockMoviesRepositoryContract = mock(MoviesRepositoryContract::class.java)
    private val mockGenreRepositoryContract = mock(GenresRepositoryContract::class.java)
    private val mockMovieMapper = mock(MovieMapper::class.java)
    private val mockGenreNameMapper = mock(GenreNameMapperTest::class.java)

    private val getPopularMovieUseCase =
        GetPopularMoviesUseCase(mockMoviesRepositoryContract, mockGenreRepositoryContract, mockMovieMapper, mockGenreNameMapper)

    private val testObserver = TestObserver<List<Movie>>()
    private val pageNumber = 1

    @Test
    fun `WHEN getPopularMovies is called AND a repository fails THEN verify an exception is returned`() {
        // Arrange
        given(mockMoviesRepositoryContract.getPopularMovies(pageNumber))
            .willReturn(Single.error(Exception()))

        // Act
        getPopularMovieUseCase.getPopularMovies(pageNumber).subscribe(testObserver)

        // Arrange
        testObserver.assertError(Exception::class.java)
    }

    @Test
    fun `WHEN getPopularMovies is called AND popular movies and genres are retrieved from the repositories THEN verify mapped domain movies are returned`() {
        // Arrange
        val movieEntities = listOf(createMockMovieEntity())
        val genreEntities = listOf(createGenreEntity())
        val movies = listOf(createMockMovie())
        val genreNameMap = mapOf(mockGenreId to mockGenreName)

        given(mockMoviesRepositoryContract.getPopularMovies(pageNumber))
            .willReturn(Single.just(movieEntities))
        given(mockGenreRepositoryContract.getGenres())
            .willReturn(Single.just(genreEntities))
        given(mockMovieMapper.toDomainModel(movieEntities, genreNameMap))
            .willReturn(movies)
        given(mockGenreNameMapper.toNameMap(genreEntities))
            .willReturn(genreNameMap)

        // Act
        getPopularMovieUseCase.getPopularMovies(pageNumber).subscribe(testObserver)

        // Assert
        testObserver.assertResult(movies)
    }
}
