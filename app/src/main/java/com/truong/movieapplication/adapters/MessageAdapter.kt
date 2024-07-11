package com.truong.movieapplication.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.truong.movieapplication.R
import com.truong.movieapplication.data.models.Message
import com.truong.movieapplication.ui.mainscreen.notification.MessageDetailActivity
import com.truong.movieapplication.ui.mainscreen.notification.TypeMessages

class MessageAdapter(private val mMessages: List<Message>) : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val mTitle = itemView.findViewById<TextView>(R.id.message_title)
        private val mImage = itemView.findViewById<ImageView>(R.id.message_image)

        fun bind(message: Message) {
            mTitle.text = message.message
            Glide.with(itemView.context).load(TypeMessages.TYPE_MESSAGE_ICON[message.type]).into(mImage)
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, MessageDetailActivity::class.java)
                intent.putExtra("message", message.message)
                intent.putExtra("title", message.title)
                intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MessageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_message, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(mMessages[position])
    }

    override fun getItemCount(): Int {
        return mMessages.size
    }
}