package com.robdir.themoviedb.core

import java.text.NumberFormat

interface LocaleProvider {

    fun getNumberFormat(): NumberFormat

    fun getDisplayLanguage(languageCode: String): String
}
