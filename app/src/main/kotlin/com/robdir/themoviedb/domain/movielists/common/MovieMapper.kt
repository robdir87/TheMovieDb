package com.robdir.themoviedb.domain.movielists.common

import com.robdir.themoviedb.data.movies.MovieEntity
import com.robdir.themoviedb.domain.GenreNameMapper
import javax.inject.Inject

class MovieMapper @Inject constructor(private val genreNameMapper: GenreNameMapper) {
    fun toDomainModel(movieEntity: MovieEntity, genreMap: Map<Int, String>): Movie = with(movieEntity) {
        Movie(
            id, title, popularity, posterPath, releaseDate, genreNameMapper.toNameList(genreIds, genreMap)
        )
    }

    fun toDomainModel(movieEntities: List<MovieEntity>, genreMap: Map<Int, String>): List<Movie> =
        movieEntities.map { toDomainModel(it, genreMap) }
}
