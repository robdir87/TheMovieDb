package com.robdir.themoviedb.domain.moviedetails

data class MovieDetail(
    val id: Int,
    val homepage: String?,
    val overview: String?,
    val runtime: Int?,
    val revenue: Int,
    val spokenLanguages: List<String>
)
