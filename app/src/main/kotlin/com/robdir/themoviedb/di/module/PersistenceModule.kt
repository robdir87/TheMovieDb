package com.robdir.themoviedb.di.module

import android.arch.persistence.room.Room
import android.content.Context
import com.robdir.themoviedb.data.persistence.AppDatabase
import com.robdir.themoviedb.data.persistence.DATABASE_NAME
import com.robdir.themoviedb.data.persistence.GenresDao
import com.robdir.themoviedb.data.persistence.PopularMoviesDao
import dagger.Module
import dagger.Provides

@Module
object PersistenceModule {

    @Provides
    @JvmStatic
    fun provideDatabase(context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
            .build()

    @Provides
    @JvmStatic
    fun providePopularMoviesDao(database: AppDatabase): PopularMoviesDao =
        database.getPopularMoviesDao()

    @Provides
    @JvmStatic
    fun provideGenresDao(database: AppDatabase): GenresDao = database.getGenresDao()
}
