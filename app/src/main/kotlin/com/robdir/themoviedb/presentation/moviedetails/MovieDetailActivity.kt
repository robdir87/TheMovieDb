package com.robdir.themoviedb.presentation.moviedetails

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.annotation.StringRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.robdir.themoviedb.R
import com.robdir.themoviedb.core.LocaleProvider
import com.robdir.themoviedb.core.isLollipopOrLater
import com.robdir.themoviedb.presentation.base.BaseActivity
import com.robdir.themoviedb.presentation.common.visibleIf
import com.robdir.themoviedb.presentation.movielists.common.MovieModel
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_movie_detail.*
import kotlinx.android.synthetic.main.layout_movie_description.view.*
import kotlinx.android.synthetic.main.layout_movie_detail_row.view.*
import javax.inject.Inject

class MovieDetailActivity : BaseActivity() {

    companion object {
        val TAG: String = MovieDetailActivity::class.java.simpleName

        private const val EXTRA_MOVIE = "movie"

        fun intent(context: Context, movie: MovieModel): Intent =
            Intent(context, MovieDetailActivity::class.java).putExtra(EXTRA_MOVIE, movie)
    }

    // region Injected properties
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var picasso: Picasso

    @Inject
    lateinit var localeProvider: LocaleProvider
    // endregion

    private lateinit var movieDetailViewModel: MovieDetailViewModel

    // region Lifecycle methods
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        supportPostponeEnterTransition()

        movieDetailViewModel =
            ViewModelProviders.of(this, viewModelFactory)[MovieDetailViewModel::class.java]
                .apply {
                    movieDetail.observe(this@MovieDetailActivity,
                        Observer<MovieDetailModel> { movieDetail -> displayMovieDetail(movieDetail) }
                    )
                }

        setupToolbar()
        displayMovieBasicDataAndLoadDetail()
    }
    // endregion

    // region Override methods
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
    // endregion

    // region Private methods
    private fun setupToolbar() {
        setSupportActionBar(toolbarMovieDetail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun displayMovieBasicDataAndLoadDetail() {
        intent?.getParcelableExtra<MovieModel>(EXTRA_MOVIE)?.apply {

            if (isLollipopOrLater()) imageViewMoviePoster.transitionName = "$id"
            picasso.load(posterUrl)
                .noFade()
                .into(imageViewMoviePoster, object : Callback {
                    override fun onSuccess() {
                        supportStartPostponedEnterTransition()
                    }

                    override fun onError(e: java.lang.Exception?) {
                        supportStartPostponedEnterTransition()
                    }
                })

            toolbarLayoutMovieDetail.title = title

            layoutMovieDetailContainer.run {
                addDetailRow(R.string.movie_detail_release_year, releaseYear)
                addDetailRow(
                    R.string.movie_detail_genres,
                    genres.joinToString(context.getString(R.string.genre_separator))
                )
                addDetailRow(R.string.movie_detail_popularity, "$popularity")
            }

            movieDetailViewModel.getMovieDetail(id)
        }
    }

    private fun displayMovieDetail(movieDetail: MovieDetailModel?) {
        movieDetail?.apply {
            fabMovieHomePage.run {
                visibleIf(!homepage.isNullOrEmpty())
                setOnClickListener { goToHomePage(homepage.orEmpty()) }
            }

            layoutMovieDetailContainer.run {
                layoutMovieDetailContainer.addDescriptionRow(overview)
                runtime?.let { addDetailRow(R.string.movie_detail_runtime, getString(R.string.movie_detail_runtime_value, it)) }
                addDetailRow(
                    R.string.movie_detail_revenue,
                    getString(R.string.movie_detail_revenue_format, localeProvider.getNumberFormat().format(revenue))
                )
                addDetailRow(R.string.movie_detail_language, originalLanguage)
            }
        }
    }

    private fun ViewGroup.addDetailRow(
        @StringRes detailLabelResourceId: Int,
        detailValue: String
    ) {
        val detailRow: View = LayoutInflater.from(context)
            .inflate(R.layout.layout_movie_detail_row, this, false)

        addView(detailRow)

        detailRow.apply {
            textViewMovieDetailRowLabel.setText(detailLabelResourceId)
            textViewMovieDetailRowValue.text = detailValue
        }
    }

    private fun ViewGroup.addDescriptionRow(description: String?) {
        description?.let {
            addView(LayoutInflater.from(context)
                .inflate(R.layout.layout_movie_description, this, false), 0)

            textViewMovieDescription.text = it
        }
    }

    private fun goToHomePage(url: String) {
        try {
            startActivity(Intent(Intent.ACTION_VIEW).setData(Uri.parse(url)))
        } catch (exception: Exception) {
            // If the user has no browser, just fail silently
        }
    }
    // endregion
}
