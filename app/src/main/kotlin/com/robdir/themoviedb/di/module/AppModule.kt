package com.robdir.themoviedb.di.module

import android.content.Context
import com.robdir.themoviedb.App
import com.robdir.themoviedb.core.DeviceLocaleProvider
import com.robdir.themoviedb.core.LocaleProvider
import com.robdir.themoviedb.core.ReactivexSchedulerProvider
import com.robdir.themoviedb.core.SchedulerProvider
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
        fun bindContext(app: App): Context

        @Binds
        @AppScope
        fun bindSchedulerProvider(reactivexSchedulerProvider: ReactivexSchedulerProvider): SchedulerProvider

        @Binds
        fun bindLocaleProvider(deviceLocaleProvider: DeviceLocaleProvider): LocaleProvider
    }

    @Provides
    @AppScope
    @JvmStatic
    fun provideMovieApi(retrofit: Retrofit): MovieApi = retrofit.create(MovieApi::class.java)
}
