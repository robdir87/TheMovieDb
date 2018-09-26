package com.robdir.themoviedb.domain.moviedetails

import com.robdir.themoviedb.data.movies.MovieDetailEntity
import javax.inject.Inject

class MovieDetailMapper @Inject constructor() {
    fun toDomainModel(movieDetailEntity: MovieDetailEntity): MovieDetail = with(movieDetailEntity) {
        MovieDetail(
            id = id,
            homepage = homepage,
            overview = overview,
            runtime = runtime,
            revenue = revenue,
            spokenLanguages = spokenLanguages.map { it.name }
        )
    }
}
