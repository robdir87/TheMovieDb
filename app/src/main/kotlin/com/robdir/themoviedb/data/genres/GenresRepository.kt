package com.robdir.themoviedb.data.genres

import com.robdir.themoviedb.data.MovieApi
import com.robdir.themoviedb.data.persistence.GenresDao
import io.reactivex.Single
import javax.inject.Inject

class GenresRepository @Inject constructor(
    private val movieApi: MovieApi,
    private val genresDao: GenresDao
) : GenresRepositoryContract {

    override fun getGenres(): Single<List<GenreEntity>> = movieApi.getGenres().map { it.genres }

    // region Private methods
    private fun getGenresFromDatabase(): Single<List<GenreEntity>> =
        genresDao.getGenres()

    private fun saveGenresToDatabase(genres: List<GenreEntity>, clearBefore: Boolean = false) {
        if (clearBefore) genresDao.deleteGenres()
        genresDao.saveGenres(genres)
    }
    // endregion
}
