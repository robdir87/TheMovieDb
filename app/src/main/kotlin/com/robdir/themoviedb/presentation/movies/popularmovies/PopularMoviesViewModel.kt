package com.robdir.themoviedb.presentation.movies.popularmovies

import android.arch.lifecycle.MutableLiveData
import android.support.annotation.VisibleForTesting
import com.robdir.themoviedb.core.NetworkInfoProvider
import com.robdir.themoviedb.core.SchedulerProvider
import com.robdir.themoviedb.domain.popularmovies.GetPopularMoviesContract
import com.robdir.themoviedb.presentation.base.BaseViewModel
import com.robdir.themoviedb.presentation.common.TheMovieDbError
import com.robdir.themoviedb.presentation.movies.common.MovieModel
import com.robdir.themoviedb.presentation.movies.common.MovieModelMapper
import java.io.IOException
import javax.inject.Inject

class PopularMoviesViewModel @Inject constructor(
    private val getPopularMoviesContract: GetPopularMoviesContract,
    private val movieModelMapper: MovieModelMapper,
    private val networkInfoProvider: NetworkInfoProvider,
    schedulerProvider: SchedulerProvider
) : BaseViewModel(schedulerProvider) {

    val movies: MutableLiveData<List<MovieModel>> = MutableLiveData()
    val isLoading: MutableLiveData<Boolean> = MutableLiveData()

    fun getPopularMovies() {
        isLoading.value = true

        getPopularMoviesContract.getPopularMovies()
            .map { movieModelMapper.toPresentationModel(it) }
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.mainThread())
            .subscribe(
                { movies ->
                    isLoading.value = false
                    if (movies.isEmpty()) error.value = createTheMovieDbError() else this.movies.value = movies
                },
                { error ->
                    isLoading.value = false

                    if (error is IOException && !networkInfoProvider.isNetworkAvailable()) {
                        this.networkError.value = createTheMovieDbError()
                    } else {
                        this.error.value = createTheMovieDbError()
                    }
                }
            )
    }

    @VisibleForTesting
    fun createTheMovieDbError() = TheMovieDbError()
}
