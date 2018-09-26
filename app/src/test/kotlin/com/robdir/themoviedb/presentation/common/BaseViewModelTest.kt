package com.robdir.themoviedb.presentation.common

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import android.support.annotation.CallSuper
import com.robdir.themoviedb.TestSchedulerProvider
import com.robdir.themoviedb.core.NetworkInfoProvider
import com.robdir.themoviedb.mock
import com.robdir.themoviedb.presentation.base.BaseViewModel
import org.junit.Before
import org.junit.Rule
import org.mockito.Mockito.doReturn

abstract class BaseViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    protected val testSchedulerProvider = TestSchedulerProvider()
    protected val mockNetworkInfoProvider = mock<NetworkInfoProvider>()

    protected val mockNetworkErrorObserver = mock<Observer<TheMovieDbError>>()
    protected val mockErrorObserver = mock<Observer<TheMovieDbError>>()
    protected val mockIsLoadingObserver = mock<Observer<Boolean>>()

    protected val error = TheMovieDbError()

    protected abstract fun getViewModel(): BaseViewModel

    @Before
    @CallSuper
    open fun setup() {
        with(getViewModel()) {
            error.observeForever(mockErrorObserver)
            networkError.observeForever(mockNetworkErrorObserver)
        }

        doReturn(error).`when`(getViewModel()).createTheMovieDbError()
    }
}
