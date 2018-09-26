package com.robdir.themoviedb.presentation.moviedetails

data class MovieDetailModel(
    val id: Int,
    val homepage: String?,
    val overview: String?,
    val runtime: Int?,
    val revenue: Int,
    val spokenLanguages: List<String>
)
