package com.robdir.themoviedb.data.movies

import com.robdir.themoviedb.data.genres.GenreEntity

data class MovieDetailEntity(
    val id: Int,
    val homepage: String?,
    val overview: String?,
    val runtime: Int?,
    val revenue: Int,
    val spokenLanguages: List<LanguageEntity>
)
