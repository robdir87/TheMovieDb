package com.robdir.themoviedb.data.persistence

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.robdir.themoviedb.BuildConfig
import com.robdir.themoviedb.data.genres.GenreEntity
import com.robdir.themoviedb.data.movies.MovieEntity

const val DATABASE_NAME = "com.robdir.themoviedb"

// region SQL Commands
const val SELECT_ALL_FROM = "select * from"
const val DELETE_FROM = "delete from"
// endregion

@Database(
    version = BuildConfig.DATABASE_VERSION,
    exportSchema = false,
    entities = [
        MovieEntity::class,
        GenreEntity::class
    ]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getPopularMoviesDao(): PopularMoviesDao

    abstract fun getGenresDao(): GenresDao
}
