package com.robdir.themoviedb.domain.moviedetails

import com.robdir.themoviedb.data.movies.MoviesRepositoryContract
import io.reactivex.Single
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val moviesRepositoryContract: MoviesRepositoryContract,
    private val movieDetailMapper: MovieDetailMapper
) : GetMovieDetailsContract {
    override fun getMovieDetail(movieId: Int): Single<MovieDetail> =
        moviesRepositoryContract.getMovieDetails(movieId)
            .map { movieDetailMapper.toDomainModel(it) }
}
