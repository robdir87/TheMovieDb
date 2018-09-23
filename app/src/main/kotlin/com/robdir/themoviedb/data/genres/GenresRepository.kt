package com.robdir.themoviedb.data.genres

import com.robdir.themoviedb.data.MovieApi
import io.reactivex.Single
import javax.inject.Inject

class GenresRepository @Inject constructor(
    private val movieApi: MovieApi
) : GenresRepositoryContract {

    override fun getGenres(): Single<List<GenreEntity>> = movieApi.getGenres().map { it.genres }
}
