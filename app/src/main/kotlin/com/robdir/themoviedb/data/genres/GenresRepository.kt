package com.robdir.themoviedb.data.genres

import android.support.annotation.VisibleForTesting
import com.robdir.themoviedb.data.MovieApi
import com.robdir.themoviedb.data.persistence.GenresDao
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class GenresRepository @Inject constructor(
    private val movieApi: MovieApi,
    private val genresDao: GenresDao
) : GenresRepositoryContract {

    override fun getGenres(forceUpdate: Boolean): Single<List<GenreEntity>> =
        if (forceUpdate) {
            deleteGenresFromDatabase().andThen(getGenresFromApi())
        } else {
            genresDao.getGenres()
                .flatMap { genres ->
                    if (genres.isEmpty()) getGenresFromApi() else Single.just(genres)
                }.onErrorResumeNext { getGenresFromApi() }
        }

    // region Private methods
    @VisibleForTesting
    fun getGenresFromApi(): Single<List<GenreEntity>> =
        movieApi.getGenres().map { it.genres }.doOnSuccess(genresDao::saveGenres)

    private fun deleteGenresFromDatabase(): Completable = Completable.create { emitter ->
        genresDao.deleteGenres()
        emitter.onComplete()
    }
    // endregion
}
