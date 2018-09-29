package com.robdir.themoviedb.presentation.movielists.searchmovies

import android.arch.lifecycle.MutableLiveData
import com.robdir.themoviedb.core.NetworkInfoProvider
import com.robdir.themoviedb.core.SchedulerProvider
import com.robdir.themoviedb.domain.movielists.searchmovies.SearchMoviesContract
import com.robdir.themoviedb.presentation.base.BaseViewModel
import com.robdir.themoviedb.presentation.movielists.common.MovieModel
import com.robdir.themoviedb.presentation.movielists.common.MovieModelMapper
import javax.inject.Inject

class SearchMoviesViewModel @Inject constructor(
    private val searchMoviesContract: SearchMoviesContract,
    private val movieModelMapper: MovieModelMapper,
    networkInfoProvider: NetworkInfoProvider,
    schedulerProvider: SchedulerProvider
) : BaseViewModel(schedulerProvider, networkInfoProvider) {

    var movies: MutableLiveData<List<MovieModel>> = MutableLiveData()

    fun searchMovies(query: String) {
        if (query.isNotEmpty()) {
            searchMoviesContract.searchMovies(query)
                .map { movieModelMapper.toPresentationModel(it) }
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe(
                    { movies -> this.movies.value = movies },
                    { error -> manageError(error) }
                )
        } else {
            movies.value = emptyList()
        }
    }
}
