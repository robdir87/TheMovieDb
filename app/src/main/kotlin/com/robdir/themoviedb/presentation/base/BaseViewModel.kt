package com.robdir.themoviedb.presentation.base

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.support.annotation.VisibleForTesting
import com.robdir.themoviedb.core.NetworkInfoProvider
import com.robdir.themoviedb.core.SchedulerProvider
import com.robdir.themoviedb.presentation.common.TheMovieDbError
import java.io.IOException

abstract class BaseViewModel(
    protected val schedulerProvider: SchedulerProvider,
    private val networkInfoProvider: NetworkInfoProvider
) : ViewModel() {

    val networkError: MutableLiveData<TheMovieDbError> = MutableLiveData()
    val error: MutableLiveData<TheMovieDbError> = MutableLiveData()
    val isLoading: MutableLiveData<Boolean> = MutableLiveData()

    @VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    fun createTheMovieDbError() = TheMovieDbError()

    protected fun manageError(error: Throwable) {
        if (error is IOException && !networkInfoProvider.isNetworkAvailable()) {
            this.networkError.value = createTheMovieDbError()
        } else {
            this.error.value = createTheMovieDbError()
        }
    }
}
