package com.robdir.themoviedb.data.genres

import io.reactivex.Single

interface GenresRepositoryContract {
    fun getGenres(forceUpdate: Boolean): Single<List<GenreEntity>>
}
