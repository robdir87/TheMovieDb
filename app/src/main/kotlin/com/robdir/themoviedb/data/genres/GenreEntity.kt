package com.robdir.themoviedb.data.genres

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey

const val GENRES_TABLE_NAME = "genres"

@Entity(tableName = GENRES_TABLE_NAME)
data class GenreEntity(
    @PrimaryKey val id: Int,
    val name: String
) {
    @Ignore constructor() : this(
        id = 0,
        name = ""
    )
}
