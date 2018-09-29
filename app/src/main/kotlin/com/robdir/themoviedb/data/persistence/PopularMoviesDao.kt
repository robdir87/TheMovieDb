package com.robdir.themoviedb.data.persistence

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import com.robdir.themoviedb.data.movies.COLUMN_NAME_POPULARITY
import com.robdir.themoviedb.data.movies.MovieEntity
import com.robdir.themoviedb.data.movies.POPULAR_MOVIES_TABLE_NAME
import io.reactivex.Single

@Dao
interface PopularMoviesDao {

    @Query(value = "$SELECT_ALL_FROM $POPULAR_MOVIES_TABLE_NAME $ORDER_BY $COLUMN_NAME_POPULARITY $DESC")
    fun getPopularMovies(): Single<List<MovieEntity>>

    @Insert(onConflict = REPLACE)
    fun savePopularMovies(popularMovies: List<MovieEntity>)

    @Query(value = "$DELETE_FROM $POPULAR_MOVIES_TABLE_NAME")
    fun deleteMovies()
}
