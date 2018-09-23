package com.robdir.themoviedb.data.movies

data class SearchResultEntity(
    val page: Int,
    val totalResults: Int,
    val totalPages: Int,
    val results: List<MovieEntity>
)
