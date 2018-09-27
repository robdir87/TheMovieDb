package com.robdir.themoviedb.data.movies

data class MovieDetailEntity(
    val id: Int,
    val homepage: String?,
    val overview: String?,
    val runtime: Int?,
    val revenue: Int,
    val originalLanguage: String
)
