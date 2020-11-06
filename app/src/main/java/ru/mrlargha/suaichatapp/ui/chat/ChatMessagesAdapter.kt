package ru.mrlargha.suaichatapp.ui.chat

import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.mrlargha.suaichatapp.databinding.MessageViewBinding
import ru.mrlargha.suaichatapp.models.ChatMessage

class ChatMessagesAdapter(val messages: MutableList<ChatMessage>, private val userSelfId: Long) :
    RecyclerView.Adapter<ChatMessagesAdapter.MessageViewHolder>() {

    inner class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(message: ChatMessage) {
            val binding = MessageViewBinding.bind(itemView)

            binding.apply {
                if(message.user.id == userSelfId) {
                    selfImage.visibility = VISIBLE
                    otherUserImage.visibility = GONE
                    messageCard.visibility = VISIBLE
                    messageCardOther.visibility = GONE

                    messageContent.text = message.content
                    author.text = message.user.firstName + " " + message.user.lastName
                    date.text = message.date.toString()
                } else {
                    selfImage.visibility = GONE
                    otherUserImage.visibility = VISIBLE
                    messageCard.visibility = GONE
                    messageCardOther.visibility = VISIBLE
                    Picasso.get().load("http://fspobot.tw1.ru:8080/profile/getImage/${message.user.avatarName}").into(otherUserImage)
                    messageContentOther.text = message.content
                    authorOther.text = message.user.firstName + " " + message.user.lastName
                    dateOther.text = message.date.toString()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MessageViewHolder(
            MessageViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ).root
        )

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(messages[position])
    }

    override fun getItemCount() = messages.size
}