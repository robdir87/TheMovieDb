package com.robdir.themoviedb.data

import com.robdir.themoviedb.BuildConfig
import com.robdir.themoviedb.data.genres.GetGenresResponseEntity
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

private const val DEFAULT_LANGUAGE = "en_US"

interface MovieApi {

    @GET("genre/movie/list")
    fun getGenres(
        @Query("api_key") apiKey: String = BuildConfig.THE_MOVIE_DB_API_KEY,
        @Query("language") language: String = DEFAULT_LANGUAGE
    ): Single<GetGenresResponseEntity>
}
