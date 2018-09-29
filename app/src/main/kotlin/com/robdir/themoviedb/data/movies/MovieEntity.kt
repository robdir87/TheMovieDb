package com.robdir.themoviedb.data.movies

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey

const val POPULAR_MOVIES_TABLE_NAME = "popular_movies"
const val COLUMN_NAME_POPULARITY = "popularity"

@Entity(tableName = POPULAR_MOVIES_TABLE_NAME)
data class MovieEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: String,
    val genreIds: List<Int>
) {
    @Ignore constructor() : this(
        id = 0,
        title = "",
        popularity = 0.0,
        posterPath = "",
        releaseDate = "",
        genreIds = emptyList()
    )
}
