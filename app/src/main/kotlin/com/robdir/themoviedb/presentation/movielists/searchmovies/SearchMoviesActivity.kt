package com.robdir.themoviedb.presentation.movielists.searchmovies

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.view.ViewCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.widget.ImageView
import com.robdir.themoviedb.R
import com.robdir.themoviedb.core.SchedulerProvider
import com.robdir.themoviedb.presentation.base.BaseActivity
import com.robdir.themoviedb.presentation.common.TheMovieDbError
import com.robdir.themoviedb.presentation.common.gone
import com.robdir.themoviedb.presentation.common.visible
import com.robdir.themoviedb.presentation.moviedetails.MovieDetailActivity
import com.robdir.themoviedb.presentation.movielists.common.MovieAdapter
import com.robdir.themoviedb.presentation.movielists.common.MovieModel
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import kotlinx.android.synthetic.main.activity_search_movies.*
import kotlinx.android.synthetic.main.layout_no_movies.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

const val SEARCH_DEBOUNCE_TIME_IN_MILLISECONDS = 500L

class SearchMoviesActivity :
    BaseActivity(),
    MovieAdapter.Callback {

    companion object {
        val TAG: String = SearchMoviesActivity::class.java.simpleName

        fun intent(context: Context): Intent = Intent(context, SearchMoviesActivity::class.java)
    }

    // region Injected properties
    @Inject
    lateinit var movieAdapter: MovieAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var schedulerProvider: SchedulerProvider
    // endregion

    private lateinit var searchMoviesViewModel: SearchMoviesViewModel

    // region Lifecycle methods
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_movies)

        setupRecyclerViewMovies()
        setupSearchView()
        setupToolbar()

        searchMoviesViewModel =
            ViewModelProviders.of(this, viewModelFactory)[SearchMoviesViewModel::class.java]
                .apply {
                    movies.observe(this@SearchMoviesActivity,
                        Observer<List<MovieModel>> { movies -> displayMovies(movies.orEmpty()) }
                    )

                    error.observe(this@SearchMoviesActivity,
                        Observer<TheMovieDbError> { manageError(R.string.movies_not_available_error_message) }
                    )

                    networkError.observe(this@SearchMoviesActivity,
                        Observer<TheMovieDbError> { manageError(R.string.network_error_message) }
                    )
                }

        textViewNoMoviesAction.gone()
    }
    // endregion

    // region Override methods
    override fun onMovieSelected(movie: MovieModel, imageViewMoviePoster: ImageView) {
        startActivity(
            MovieDetailActivity.intent(this, movie),
            ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,
                imageViewMoviePoster,
                ViewCompat.getTransitionName(imageViewMoviePoster)
            ).toBundle()
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
    // endregion

    // region Private methods
    private fun setupToolbar() {
        setSupportActionBar(toolbarSearchMovies)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupRecyclerViewMovies() {
        recyclerViewMovies.apply {
            adapter = movieAdapter
            layoutManager = LinearLayoutManager(context)
        }
        movieAdapter.callback = this
    }

    private fun setupSearchView() {
        searchViewMovies.run {
            setIconifiedByDefault(false)

            Observable.create(
                ObservableOnSubscribe<String> { subscriber ->
                    setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                        override fun onQueryTextChange(query: String?): Boolean {
                            subscriber.onNext(query.orEmpty())
                            return true
                        }

                        override fun onQueryTextSubmit(query: String?): Boolean = false
                    })
                }
            ).debounce(SEARCH_DEBOUNCE_TIME_IN_MILLISECONDS, TimeUnit.MILLISECONDS)
                .observeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe { query -> searchMoviesViewModel.searchMovies(query) }
        }
    }

    private fun displayMovies(movies: List<MovieModel>) {
        recyclerViewMovies.visible()
        layoutNoMovies.gone()

        movieAdapter.movies = movies
    }

    private fun manageError(@StringRes messageId: Int) {
        movieAdapter.movies = emptyList()

        layoutNoMovies.visible()
        textViewNoMoviesMessage.setText(messageId)
    }
    // endregion
}
