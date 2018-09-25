package com.robdir.themoviedb.core

import io.reactivex.Scheduler

interface SchedulerProvider {
    fun mainThread(): Scheduler

    fun computation(): Scheduler

    fun io(): Scheduler
}
