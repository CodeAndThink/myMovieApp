package com.truong.movieapplication.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.truong.movieapplication.R
import com.truong.movieapplication.data.connections.network.Base
import com.truong.movieapplication.data.models.Movie

class TopRateMovieAdapter(private val mList: List<Movie>) : RecyclerView.Adapter<TopRateMovieAdapter.ViewHolder>(){

    private var onClickListener: OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.top_rate_movie_item, parent, false)

        return ViewHolder(view)
    }

    fun setOnClickListener(listener: OnClickListener?) {
        this.onClickListener = listener
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mList[position])
        holder.itemView.setOnClickListener {
            onClickListener?.onClick(position, mList[position])
        }
    }

    interface OnClickListener {
        fun onClick(position: Int, movie: Movie)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val movieImage: ImageView = this.itemView.findViewById(R.id.movie_small_pic)
        private val movieName: TextView = this.itemView.findViewById(R.id.movie_small_name)
        private val movieDate: TextView = this.itemView.findViewById(R.id.movie_small_date)

        fun bind(movie: Movie) {
            movieName.text = movie.title
            Glide.with(itemView.context).load(Base.IMAGE_URL + Base.SIZE + movie.poster_path).into(movieImage)
            movieDate.text = movie.release_date
        }
    }
}