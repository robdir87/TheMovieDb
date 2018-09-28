package com.robdir.themoviedb.presentation.movielists.popularmovies

import android.arch.lifecycle.MutableLiveData
import com.robdir.themoviedb.core.NetworkInfoProvider
import com.robdir.themoviedb.core.SchedulerProvider
import com.robdir.themoviedb.domain.movielists.popularmovies.GetPopularMoviesContract
import com.robdir.themoviedb.presentation.base.BaseViewModel
import com.robdir.themoviedb.presentation.movielists.common.MovieModel
import com.robdir.themoviedb.presentation.movielists.common.MovieModelMapper
import javax.inject.Inject

class PopularMoviesViewModel @Inject constructor(
    private val getPopularMoviesContract: GetPopularMoviesContract,
    private val movieModelMapper: MovieModelMapper,
    networkInfoProvider: NetworkInfoProvider,
    schedulerProvider: SchedulerProvider
) : BaseViewModel(schedulerProvider, networkInfoProvider) {

    val movies: MutableLiveData<List<MovieModel>> = MutableLiveData()

    fun getPopularMovies(fromSwipeToRefresh: Boolean) {
        isLoading.value = true

        getPopularMoviesContract.getPopularMovies(forceUpdate = fromSwipeToRefresh)
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
                    manageError(error)
                }
            )
    }
}
