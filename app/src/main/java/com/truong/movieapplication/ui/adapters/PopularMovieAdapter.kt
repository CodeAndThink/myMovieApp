package com.truong.movieapplication.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.truong.movieapplication.R
import com.truong.movieapplication.data.connections.network.Base
import com.truong.movieapplication.data.models.Movie

class PopularMovieAdapter(mList: List<Movie>, private val viewPager2: ViewPager2) : RecyclerView.Adapter<PopularMovieAdapter.ViewHolder>() {

    private var onClickListener: OnClickListener? = null
    private val newList: List<Movie> =
        listOf(mList.last()) + mList + listOf(mList.first())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.popular_movie_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(newList[position])
        holder.itemView.setOnClickListener {
            onClickListener?.onClick(position, newList[position])
        }
    }

    fun setOnClickListener(listener: OnClickListener?) {
        this.onClickListener = listener
    }

    override fun getItemCount(): Int {
        return newList.size
    }

    interface OnClickListener {
        fun onClick(position: Int, movie: Movie)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val movieImage: ImageView = this.itemView.findViewById(R.id.movie_big_pic)
        private val movieName: TextView = this.itemView.findViewById(R.id.movie_big_name)
        private val movieDate: TextView = this.itemView.findViewById(R.id.movie_big_date)
        private val movieRatingBar: RatingBar = this.itemView.findViewById(R.id.movie_big_rating)
        private val movieRatingScore: TextView = this.itemView.findViewById(R.id.movie_big_rating_score)

        fun bind(movie: Movie) {
            movieName.text = movie.title
            Glide.with(itemView.context).load(Base.IMAGE_URL + Base.SIZE + movie.poster_path).into(movieImage)
            movieDate.text = movie.release_date
            movieRatingBar.rating = movie.vote_average / 2
            val ratingScore = StringBuilder()
            ratingScore.append(movie.vote_average)
            ratingScore.append("/10")
            movieRatingScore.text = ratingScore
        }
    }
}