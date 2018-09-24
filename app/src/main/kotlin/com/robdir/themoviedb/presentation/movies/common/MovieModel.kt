package com.robdir.themoviedb.presentation.movies.common

import android.os.Parcel
import android.os.Parcelable

data class MovieModel(
    val id: Int,
    val title: String,
    val popularity: Int,
    val posterUrl: String,
    val releaseYear: String,
    val genres: String
) : Parcelable {

    companion object {
        @JvmField
        val CREATOR = object : Parcelable.Creator<MovieModel> {
            override fun createFromParcel(source: Parcel): MovieModel =
                MovieModel(
                    id = source.readInt(),
                    title = source.readString().orEmpty(),
                    popularity = source.readInt(),
                    posterUrl = source.readString().orEmpty(),
                    releaseYear = source.readString().orEmpty(),
                    genres = source.readString().orEmpty()
                )

            override fun newArray(size: Int): Array<MovieModel?> = arrayOfNulls(size)
        }
    }

    override fun describeContents(): Int = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(id)
        writeString(title)
        writeInt(popularity)
        writeString(posterUrl)
        writeString(releaseYear)
        writeString(genres)
    }
}
