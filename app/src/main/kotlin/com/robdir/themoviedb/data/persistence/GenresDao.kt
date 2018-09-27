package com.robdir.themoviedb.data.persistence

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import com.robdir.themoviedb.data.genres.GENRES_TABLE_NAME
import com.robdir.themoviedb.data.genres.GenreEntity
import io.reactivex.Single

@Dao
interface GenresDao {

    @Query(value = "$SELECT_ALL_FROM $GENRES_TABLE_NAME")
    fun getGenres(): Single<List<GenreEntity>>

    @Insert(onConflict = REPLACE)
    fun saveGenres(popularMovies: List<GenreEntity>)

    @Query(value = "$DELETE_FROM $GENRES_TABLE_NAME")
    fun deleteGenres()
}
