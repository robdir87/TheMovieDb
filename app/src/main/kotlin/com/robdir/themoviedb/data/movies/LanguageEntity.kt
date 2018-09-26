package com.robdir.themoviedb.data.movies

import com.google.gson.annotations.SerializedName

data class LanguageEntity(
    @SerializedName("iso6391") val code: String,
    val name: String
)
