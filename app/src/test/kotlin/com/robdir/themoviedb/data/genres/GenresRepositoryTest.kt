package com.robdir.themoviedb.data.genres

import com.robdir.themoviedb.data.MovieApi
import com.robdir.themoviedb.mock
import io.reactivex.Single
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.BDDMockito.given

class GenresRepositoryTest {

    private val mockMovieApi = mock<MovieApi>()
    private val genreRepository = GenresRepository(mockMovieApi)

    @Test
    fun `WHEN getGenres is called THEN verify genres are fetched via the api`() {
        // Arrange
        val genreList: List<GenreEntity> = emptyList()
        val getGenresResponse = GetGenresResponseEntity(genreList)

        given(mockMovieApi.getGenres(apiKey = anyString(), language = anyString()))
            .willReturn(Single.just(getGenresResponse))

        // Act & Assert
        genreRepository.getGenres()
            .test()
            .assertResult(genreList)
    }
}
