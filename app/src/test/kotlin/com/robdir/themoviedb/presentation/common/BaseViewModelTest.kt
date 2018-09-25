package com.robdir.themoviedb.presentation.common

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.robdir.themoviedb.TestSchedulerProvider
import com.robdir.themoviedb.mock
import org.junit.Rule

abstract class BaseViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    protected val testSchedulerProvider = TestSchedulerProvider()

    protected val mockErrorObserver = mock<Observer<TheMovieDbError>>()
    protected val mockNetworkErrorObserver = mock<Observer<TheMovieDbError>>()
}
