package com.robdir.themoviedb.presentation.moviedetails

import com.robdir.themoviedb.domain.moviedetails.MovieDetail
import javax.inject.Inject

class MovieDetailModelMapper @Inject constructor() {
    fun toPresentationModel(movieDetail: MovieDetail): MovieDetailModel =
        with(movieDetail) {
            MovieDetailModel(
                id = id,
                homepage = homepage,
                overview = overview,
                runtime = runtime,
                revenue = revenue,
                originalLanguage = originalLanguage
            )
        }
}
