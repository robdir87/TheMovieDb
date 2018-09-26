package com.robdir.themoviedb.presentation.moviedetails

import android.arch.lifecycle.MutableLiveData
import com.robdir.themoviedb.core.NetworkInfoProvider
import com.robdir.themoviedb.core.SchedulerProvider
import com.robdir.themoviedb.domain.moviedetails.GetMovieDetailsContract
import com.robdir.themoviedb.presentation.base.BaseViewModel
import javax.inject.Inject

class MovieDetailViewModel @Inject constructor(
    private val getMovieDetailsContract: GetMovieDetailsContract,
    private val movieDetailModelMapper: MovieDetailModelMapper,
    networkInfoProvider: NetworkInfoProvider,
    schedulerProvider: SchedulerProvider
) : BaseViewModel(schedulerProvider, networkInfoProvider) {

    var movieDetail: MutableLiveData<MovieDetailModel> = MutableLiveData()

    fun getMovieDetail(movieId: Int) {
        isLoading.value = true

        getMovieDetailsContract.getMovieDetail(movieId)
            .map { movieDetailModelMapper.toPresentationModel(it) }
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.mainThread())
            .subscribe(
                { movieDetail ->
                    isLoading.value = false
                    this.movieDetail.value = movieDetail
                },
                { error ->
                    isLoading.value = false
                    manageError(error)
                }
            )
    }
}
