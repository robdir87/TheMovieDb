package com.robdir.themoviedb.presentation.movielists.common

import android.os.Parcel
import android.os.Parcelable

data class MovieModel(
    val id: Int,
    val title: String,
    val popularity: Int,
    val thumbnailUrl: String,
    val posterUrl: String,
    val releaseYear: String,
    val genres: List<String>
) : Parcelable {

    companion object {
        @JvmField
        val CREATOR = object : Parcelable.Creator<MovieModel> {
            override fun createFromParcel(source: Parcel): MovieModel =
                MovieModel(
                    id = source.readInt(),
                    title = source.readString().orEmpty(),
                    popularity = source.readInt(),
                    thumbnailUrl = source.readString().orEmpty(),
                    posterUrl = source.readString().orEmpty(),
                    releaseYear = source.readString().orEmpty(),
                    genres = arrayListOf<String>().apply { source.readStringList(this) }
                )

            override fun newArray(size: Int): Array<MovieModel?> = arrayOfNulls(size)
        }
    }

    override fun describeContents(): Int = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(id)
        writeString(title)
        writeInt(popularity)
        writeString(thumbnailUrl)
        writeString(posterUrl)
        writeString(releaseYear)
        writeStringList(genres)
    }
}
