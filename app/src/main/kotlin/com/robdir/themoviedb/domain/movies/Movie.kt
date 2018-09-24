package com.robdir.themoviedb.domain.movies

data class Movie(
    val id: Int,
    val title: String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: String,
    val genreNames: List<String>
)
