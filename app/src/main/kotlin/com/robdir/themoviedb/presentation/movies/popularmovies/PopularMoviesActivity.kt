package com.robdir.themoviedb.presentation.movies.popularmovies

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v7.widget.LinearLayoutManager
import com.robdir.themoviedb.R
import com.robdir.themoviedb.presentation.base.BaseActivity
import com.robdir.themoviedb.presentation.common.TheMovieDbError
import com.robdir.themoviedb.presentation.common.gone
import com.robdir.themoviedb.presentation.common.showConfirmCancelAlert
import com.robdir.themoviedb.presentation.common.visible
import com.robdir.themoviedb.presentation.common.visibleIf
import com.robdir.themoviedb.presentation.movies.common.MovieAdapter
import com.robdir.themoviedb.presentation.movies.common.MovieModel
import kotlinx.android.synthetic.main.activity_popular_movies.*
import kotlinx.android.synthetic.main.layout_no_popular_movies.*
import javax.inject.Inject

class PopularMoviesActivity :
    BaseActivity(),
    MovieAdapter.Callback {

    companion object {
        val TAG: String = PopularMoviesActivity::class.java.simpleName
    }

    // region Injected properties
    @Inject
    lateinit var movieAdapter: MovieAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    // endregion

    private lateinit var popularMoviesViewModel: PopularMoviesViewModel

    // region Lifecycle methods
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_popular_movies)

        setupRecyclerViewMovies()

        popularMoviesViewModel =
            ViewModelProviders.of(this, viewModelFactory)[PopularMoviesViewModel::class.java]
                .apply {
                    movies.observe(this@PopularMoviesActivity,
                        Observer<List<MovieModel>> { movies -> displayMovies(movies.orEmpty()) }
                    )

                    isLoading.observe(this@PopularMoviesActivity,
                        Observer<Boolean> { isLoading -> if (isLoading == false) hideProgress() else showProgress() }
                    )

                    error.observe(this@PopularMoviesActivity,
                        Observer<TheMovieDbError> { manageError(R.string.generic_error) }
                    )

                    networkError.observe(this@PopularMoviesActivity,
                        Observer<TheMovieDbError> { manageError(R.string.network_error_message) }
                    )
                }

        textViewPopularMovieEmptyListAction.setOnClickListener { loadPopularMovies() }

        loadPopularMovies()
    }
    // endregion

    // region Override methods
    override fun onMovieSelected(movie: MovieModel) {
        // TODO: go to movie detail
    }
    // endregion

    // region Private methods
    private fun setupRecyclerViewMovies() {
        swipeRefreshLayoutMovies.apply {
            setOnRefreshListener {
                movieAdapter.clear()
                loadPopularMovies()
            }
            setColorSchemeResources(R.color.colorAccent)
        }

        recyclerViewMovies.apply {
            adapter = movieAdapter
            layoutManager = LinearLayoutManager(context)
        }
        movieAdapter.callback = this
    }

    private fun displayMovies(movies: List<MovieModel>) {
        swipeRefreshLayoutMovies.visible()
        swipeRefreshLayoutMovies.isRefreshing = false
        recyclerViewMovies.visible()
        layoutEmptyPopularMovieList.gone()

        movieAdapter.addMovies(movies)
    }

    private fun loadPopularMovies() {
        popularMoviesViewModel.getPopularMovies()
    }

    private fun showProgress() {
        layoutPopularMoviesProgress.visibleIf(!swipeRefreshLayoutMovies.isRefreshing)
    }

    private fun hideProgress() {
        layoutPopularMoviesProgress.gone()
    }

    private fun manageError(@StringRes stringResId: Int) {
        swipeRefreshLayoutMovies.gone()
        swipeRefreshLayoutMovies.isRefreshing = false

        layoutEmptyPopularMovieList.visible()

        showConfirmCancelAlert(
            message = getString(stringResId),
            confirmButtonLabel = getString(R.string.alert_action_try_again),
            onConfirmButtonClicked = { _, _ -> loadPopularMovies() },
            cancelButtonLabel = getString(R.string.alert_action_ok)
        )
    }
    // endregion
}
