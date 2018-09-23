package com.robdir.themoviedb.data.genres

import com.robdir.themoviedb.data.MovieApi
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.BDDMockito.given
import org.mockito.Mockito.mock

class GenresRepositoryTest {

    private val mockMovieApi = mock(MovieApi::class.java)
    private val genreRepository = GenresRepository(mockMovieApi)
    private val testObserver = TestObserver<List<GenreEntity>>()

    @Test
    fun `WHEN getGenres is called THEN verify genres are fetched via the api`() {
        // Arrange
        val genreList: List<GenreEntity> = emptyList()
        val getGenresResponse = GetGenresResponseEntity(genreList)

        given(mockMovieApi.getGenres(apiKey = anyString(), language = anyString()))
            .willReturn(Single.just(getGenresResponse))

        // Act
        genreRepository.getGenres().subscribe(testObserver)

        // Assert
        testObserver.assertResult(genreList)
    }
}
