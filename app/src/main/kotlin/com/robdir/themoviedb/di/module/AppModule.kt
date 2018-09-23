package com.robdir.themoviedb.di.module

import android.content.Context
import com.robdir.themoviedb.App
import com.robdir.themoviedb.data.MovieApi
import com.robdir.themoviedb.di.AppScope
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module(includes = [AppModule.Binder::class])
object AppModule {

    @Module
    interface Binder {
        @Binds
        fun provideContext(app: App): Context
    }

    @Provides
    @AppScope
    @JvmStatic
    fun provideMovieApi(retrofit: Retrofit): MovieApi = retrofit.create(MovieApi::class.java)
}
