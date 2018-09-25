package com.robdir.themoviedb.presentation.movies.common

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.robdir.themoviedb.R
import com.robdir.themoviedb.presentation.common.inflate
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.layout_list_item_movie.view.*
import javax.inject.Inject
import kotlin.properties.Delegates

class MovieAdapter @Inject constructor(
    private val picasso: Picasso
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface Callback {
        fun onMovieSelected(movie: MovieModel)
    }

    var callback: Callback? = null

    var movies: List<MovieModel> by Delegates.observable(emptyList()) { _, _, _ -> notifyDataSetChanged() }

    // region Override methods
    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? MovieViewHolder)?.bind(movies[position] as? MovieModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        MovieViewHolder(parent)
    // endregion

    inner class MovieViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        parent.inflate(R.layout.layout_list_item_movie)
    ) {
        fun bind(movie: MovieModel?) {
            movie?.apply {
                itemView.run {
                    setOnClickListener { callback?.onMovieSelected(this@apply) }

                    picasso.load(posterUrl).into(imageViewMovieThumbnail)

                    textViewMovieName.text = title
                    textViewMovieYear.text = releaseYear
                    textViewPopularity.text = "$popularity"
                    textViewMovieGenres.text = genres
                }
            }
        }
    }
}
