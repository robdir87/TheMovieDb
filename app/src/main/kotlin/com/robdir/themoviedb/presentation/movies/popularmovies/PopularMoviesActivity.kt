package com.robdir.themoviedb.presentation.movies.popularmovies

import android.os.Bundle
import com.robdir.themoviedb.R
import com.robdir.themoviedb.presentation.base.BaseActivity

class PopularMoviesActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_popular_movies)
    }
}
