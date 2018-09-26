package com.robdir.themoviedb.domain.moviedetails

import io.reactivex.Single

interface GetMovieDetailsContract {
    fun getMovieDetail(movieId: Int): Single<MovieDetail>
}
