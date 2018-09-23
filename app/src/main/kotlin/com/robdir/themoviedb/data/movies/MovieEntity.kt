package com.robdir.themoviedb.data.movies

data class MovieEntity(
    val id: Int,
    val title: String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: String,
    val genreIds: List<Int>
)
