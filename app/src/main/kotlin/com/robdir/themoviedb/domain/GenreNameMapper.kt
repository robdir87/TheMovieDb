package com.robdir.themoviedb.domain

import com.robdir.themoviedb.data.genres.GenreEntity
import javax.inject.Inject

class GenreNameMapper @Inject constructor() {

    fun toNameList(genreIds: List<Int>, genreMap: Map<Int, String>): List<String> {
        val genreNames: MutableList<String> = mutableListOf()
        genreIds.forEach { genreId -> genreMap[genreId]?.let { genreNames.add(it) } }
        return genreNames
    }

    fun toNameMap(genres: List<GenreEntity>): Map<Int, String> = genres.map { it.id to it.name }.toMap()
}
