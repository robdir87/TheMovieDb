package com.robdir.themoviedb.presentation.base

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.robdir.themoviedb.core.SchedulerProvider
import com.robdir.themoviedb.presentation.common.TheMovieDbError

abstract class BaseViewModel(protected val schedulerProvider: SchedulerProvider) : ViewModel() {

    val networkError: MutableLiveData<TheMovieDbError> = MutableLiveData()
    val error: MutableLiveData<TheMovieDbError> = MutableLiveData()
}
