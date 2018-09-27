package com.robdir.themoviedb.di.module

import android.arch.persistence.room.Room
import android.content.Context
import com.robdir.themoviedb.data.persistence.AppDatabase
import com.robdir.themoviedb.data.persistence.DATABASE_NAME
import dagger.Module
import dagger.Provides

@Module
object PersistenceModule {

    @Provides
    @JvmStatic
    fun provideDatabase(context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
            .build()
}
