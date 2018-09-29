package com.robdir.themoviedb.data.persistence

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import android.arch.persistence.room.TypeConverters
import com.google.gson.reflect.TypeToken
import com.robdir.themoviedb.BuildConfig
import com.robdir.themoviedb.data.genres.GenreEntity
import com.robdir.themoviedb.data.movies.MovieEntity

const val DATABASE_NAME = "com.robdir.themoviedb"

// region SQL Commands
const val SELECT_ALL_FROM = "select * from"
const val DELETE_FROM = "delete from"
const val ORDER_BY = "order by"
const val DESC = "desc"
// endregion

@Database(
    version = BuildConfig.DATABASE_VERSION,
    exportSchema = false,
    entities = [
        MovieEntity::class,
        GenreEntity::class
    ]
)
@TypeConverters(
    IntegerListTypeConverter::class
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getPopularMoviesDao(): PopularMoviesDao

    abstract fun getGenresDao(): GenresDao
}

class IntegerListTypeConverter {
    @TypeConverter
    fun fromIntegerList(list: List<Int>): String = Gson().toJson(list)

    @TypeConverter
    fun toIntegerList(value: String): List<Int> {
        val listType = object : TypeToken<List<Int>>() {}.type
        return Gson().fromJson(value, listType)
    }
}
