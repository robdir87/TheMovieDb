package com.robdir.themoviedb.domain.moviedetails

import com.robdir.themoviedb.core.LocaleProvider
import com.robdir.themoviedb.data.movies.MovieDetailEntity
import javax.inject.Inject

class MovieDetailMapper @Inject constructor(private val localeProvider: LocaleProvider) {
    fun toDomainModel(movieDetailEntity: MovieDetailEntity): MovieDetail = with(movieDetailEntity) {
        MovieDetail(
            id = id,
            homepage = homepage,
            overview = overview,
            runtime = runtime,
            revenue = revenue,
            originalLanguage = localeProvider.getDisplayLanguage(originalLanguage)
        )
    }
}
