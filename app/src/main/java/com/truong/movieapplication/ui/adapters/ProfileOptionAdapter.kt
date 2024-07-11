package com.truong.movieapplication.ui.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.truong.movieapplication.R
import com.truong.movieapplication.ui.mainscreen.profile.options.InforComponent
import com.truong.movieapplication.ui.mainscreen.profile.options.Settings
import com.truong.movieapplication.ui.mainscreen.profile.options.WishListComponent

class ProfileOptionAdapter(private val options: List<Triple<Int, String, Int>>) : RecyclerView.Adapter<ProfileOptionAdapter.ViewHolder>() {
    private var onClickListener: OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_options, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(options[position])
        holder.itemView.setOnClickListener {
            onClickListener?.onClick(position, options[position])
        }
    }

    interface OnClickListener {
        fun onClick(position: Int, option: Triple<Int, String, Int>)
    }

    fun setOnClickListener(listener: OnClickListener?) {
        this.onClickListener = listener
    }

    override fun getItemCount(): Int {
        return options.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val mIcon: ImageView = itemView.findViewById(R.id.option_icon)
        private val mTitle: TextView = itemView.findViewById(R.id.option_title)

        fun bind(option: Triple<Int, String, Int>) {
            mTitle.text = option.second
            mIcon.setImageResource(option.third)
        }
    }
}